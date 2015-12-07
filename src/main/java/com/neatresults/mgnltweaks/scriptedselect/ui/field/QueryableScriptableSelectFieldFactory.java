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

import info.magnolia.i18nsystem.SimpleTranslator;
import info.magnolia.module.groovy.console.MgnlGroovyConsoleContext;
import info.magnolia.ui.api.context.UiContext;

import javax.inject.Inject;

import com.neatresults.mgnltweaks.scriptedselect.ui.field.QueryableScriptableSelectFieldFactory.Definition;
import com.vaadin.data.Item;

/**
 * Select field that can be provided with query and scriptlets to specify label and value for each option.
 */
public class QueryableScriptableSelectFieldFactory extends ScriptableSelectFieldFactory<Definition> {

    @Inject
    public QueryableScriptableSelectFieldFactory(Definition definition, Item relatedFieldItem, SimpleTranslator i18n, UiContext uiContext) {
        super(definition, relatedFieldItem, i18n, uiContext);
        this.definition = definition;
    }

    @Override
    protected void configureContext(MgnlGroovyConsoleContext groovyCtx) {
        groovyCtx.put("workspace", definition.getWorkspace());
        groovyCtx.put("query", definition.getQuery());
        groovyCtx.put("queryLanguage", definition.getQueryLanguage());
        groovyCtx.put("labelScript", definition.getLabelScript());
        groovyCtx.put("valueScript", definition.getValueScript());
    }

    /**
     * Definition for the class above.
     */
    public static class Definition extends ScriptableSelectFieldFactory.Definition {
        private String workspace;
        private String query;
        private String queryLanguage;
        private String labelScript;
        private String valueScript;

        public String getWorkspace() {
            return workspace;
        }

        public void setWorkspace(String workspace) {
            this.workspace = workspace;
        }

        public String getQuery() {
            return query;
        }

        public void setQuery(String query) {
            this.query = query;
        }

        public String getQueryLanguage() {
            return queryLanguage;
        }

        public void setQueryLanguage(String queryLanguage) {
            this.queryLanguage = queryLanguage;
        }

        public String getLabelScript() {
            return labelScript;
        }

        public void setLabelScript(String labelScript) {
            this.labelScript = labelScript;
        }

        public String getValueScript() {
            return valueScript;
        }

        public void setValueScript(String valueScript) {
            this.valueScript = valueScript;
        }
    }
}
