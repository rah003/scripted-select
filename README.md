Magnolia Scripted Select
=======================

A [module](https://documentation.magnolia-cms.com/display/DOCS/Modules) containing an [app](https://documentation.magnolia-cms.com/display/DOCS/Apps) for the [Magnolia CMS](http://www.magnolia-cms.com)

Scripted Select adds two custom select fields that allow developers to easily build select fields showing whatever dynamic data necessary without need to recompile everything.
Second field then allows producing select lists of items directly from any jcr based data within definition using small single line scriptlets without need to write any kind of boiler place doce

License
-------

Released under the GPLv3, see LICENSE.txt. 

Feel free to use this app, but if you modify the source code please fork us on Github.

Maven dependency
-----------------
```xml
    <dependency>
      <groupId>com.neatresults.mgnltweaks</groupId>
      <artifactId>neat-scripted-select</artifactId>
      <version>1.0-SNAPSHOT</version>
    </dependency>
```

Versions
-----------------
Version 1.0.x compatible with both currently supported version of Magnolia 5.3.x and 5.4.x

Installation & updates 
-----------------
Upon instalation, module will register 2 custom select fields and create two field definitions that are ready for inclusion and modification in dialogs either via extends or via export to yaml.
