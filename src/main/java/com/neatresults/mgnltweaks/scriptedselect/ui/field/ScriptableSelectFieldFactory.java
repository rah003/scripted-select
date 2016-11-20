/**
 *
 * Copyright 2015 by Jan Haderka <jan.haderka@neatresults.com>
 *
 * This file is part of neat-tweaks module.
 *
 * Neat-tweaks is free software: you can redistribute
 * it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either
 * version 3 of the License, or (at your option) any later version.
 *
 * Neat-tweaks is distributed in the hope that it will
 * be useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with neat-tweaks.  If not, see <http://www.gnu.org/licenses/>.
 *
 * @license GPL-3.0 <http://www.gnu.org/licenses/gpl.txt>
 *
 * Should you require distribution under alternative license in order to
 * use neat-tweaks commercially, please contact owner at the address above.
 *
 */
package com.neatresults.mgnltweaks.scriptedselect.ui.field;

import info.magnolia.cms.util.ClasspathResourcesUtil;
import info.magnolia.context.AbstractContext;
import info.magnolia.context.Context;
import info.magnolia.context.MgnlContext;
import info.magnolia.i18nsystem.SimpleTranslator;
import info.magnolia.module.groovy.console.MgnlGroovyConsole;
import info.magnolia.module.groovy.console.MgnlGroovyConsoleContext;
import info.magnolia.ui.api.context.UiContext;
import info.magnolia.ui.form.field.definition.SelectFieldDefinition;
import info.magnolia.ui.form.field.definition.SelectFieldOptionDefinition;
import info.magnolia.ui.form.field.factory.SelectFieldFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.jcr.RepositoryException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.CharEncoding;
import org.codehaus.groovy.control.CompilationFailedException;
import org.codehaus.groovy.runtime.InvokerHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.neatresults.mgnltweaks.scriptedselect.ui.field.ScriptableSelectFieldFactory.Definition;
import com.vaadin.data.Item;

import groovy.lang.Binding;
import groovy.lang.Script;

/**
 * Select field retrieving dynamically all values based on provided groovy script.
 *
 * @param <D>
 */
public class ScriptableSelectFieldFactory<D extends Definition> extends SelectFieldFactory<D> {

    private static final Logger log = LoggerFactory.getLogger(ScriptableSelectFieldFactory.class);
    private SimpleTranslator i18n;
    private UiContext uiContext;

    @Inject
    public ScriptableSelectFieldFactory(D definition, Item relatedFieldItem, SimpleTranslator i18n, UiContext uiContext) {
        super(definition, relatedFieldItem);
        this.i18n = i18n;
        this.uiContext = uiContext;
    }

    @Override
    public List<SelectFieldOptionDefinition> getSelectFieldOptionDefinition() {

        Context originalCtx = MgnlContext.getInstance();
        InputStream inputStream = null;
        MgnlGroovyConsoleContext groovyCtx = null;
        try {


            String inputFile = definition.getScript();
            // First Check
            URL inFile = ClasspathResourcesUtil.getResource(inputFile);
            if (inFile == null) {
                try {
                    inputStream = MgnlContext.getJCRSession("scripts").getNode(inputFile).getProperty("text").getStream();
                } catch (RepositoryException e) {
                    throw new RuntimeException("Can't find resource file at " + inputFile);
                }
            } else {
                // Get Input Stream
                try {
                    inputStream = ClasspathResourcesUtil.getResource(inputFile).openStream();
                } catch (IOException e) {
                    throw new RuntimeException("Can't read resource file at " + inputFile);
                }
            }
            if (inputStream == null) {
                throw new RuntimeException("Failed while reading resource file at " + inFile.getFile());
            }

            Writer writer = new StringWriter();

            groovyCtx = new MgnlGroovyConsoleContext(originalCtx);
            groovyCtx.setRepositoryStrategy(((AbstractContext) originalCtx).getRepositoryStrategy());
            configureContext(groovyCtx);

            MgnlGroovyConsole console = new MgnlGroovyConsole(new Binding());
            // set context
            MgnlContext.setInstance(groovyCtx);
            Object result = evaluate(inputStream, console.generateScriptName(), writer, console);

            List<SelectFieldOptionDefinition> fields = null;
            if (result instanceof List) {
                fields = (List<SelectFieldOptionDefinition>) result;
            } else {
                List<SelectFieldOptionDefinition> list = new ArrayList<SelectFieldOptionDefinition>();
                if (result instanceof SelectFieldOptionDefinition) {
                    list.add((SelectFieldOptionDefinition) result);
                }
                fields = list;
            }
            for (SelectFieldOptionDefinition field : fields) {
                field.setName(definition.getName());
            }
            return fields;
        } finally {
            // close files
            IOUtils.closeQuietly(inputStream);
            // restore context
            MgnlContext.setInstance(originalCtx);
        }
    }

    protected void configureContext(MgnlGroovyConsoleContext groovyCtx) {
    }

    private Object evaluate(InputStream in, String fileName, Writer out, MgnlGroovyConsole console) throws CompilationFailedException {

        Script script = null;
        try {
            script = createScript(in, out, console);
            script.setProperty("ctx", MgnlContext.getInstance());
            return script.run();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (script != null) {
                InvokerHelper.removeClass(script.getClass());
            }
        }
    }

    private Script createScript(InputStream in, Writer out, MgnlGroovyConsole console) throws CompilationFailedException, IOException {
        Script script = console.parse(new InputStreamReader(in, CharEncoding.UTF_8), console.generateScriptName());
        script.setProperty("out", out);
        script.setProperty("err", out);
        return script;
    }

    /**
     * Definition for custom select field.
     */
    public static class Definition extends SelectFieldDefinition {

        private String script;

        public Definition() {
        }

        public String getScript() {
            return script;
        }

        public void setScript(String script) {
            this.script = script;
        }
    }
}
