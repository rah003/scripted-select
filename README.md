Magnolia Scripted Select
=======================

A [module](https://documentation.magnolia-cms.com/display/DOCS/Modules) for the [Magnolia CMS](http://www.magnolia-cms.com) bringing in 2 extra select fields.

Scripted Select adds two custom select fields that allow developers to easily build select fields showing whatever dynamic data necessary without need to recompile everything.
Second field then allows producing select lists of items directly from any jcr based data within definition using small single line scriptlets without need to write any kind of boiler plate code.

More details and examples of usage can be found at [my blog](https://www.magnolia-cms.com/blogs/jan-haderka/detail~@scriptable-and-queryable-select-fields~.html).

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
      <version>2.0</version>
    </dependency>
```

Versions
-----------------
Version 1.0.x compatible with both currently supported version of Magnolia 5.3.x and 5.4.x
Version 2.0.x compatible with Magnolia 5.5.x version

Latest (2.x) version can be found at https://nexus.magnolia-cms.com/service/local/repositories/magnolia.forge.releases/content/com/neatresults/mgnltweaks/neat-scripted-select/2.0/neat-scripted-select-2.0.jar

Latest (1.x) version can be found at https://nexus.magnolia-cms.com/service/local/repositories/magnolia.forge.releases/content/com/neatresults/mgnltweaks/neat-scripted-select/1.0.2/neat-scripted-select-1.0.2.jar

Installation & updates 
-----------------
Upon instalation, module will register 2 custom select fields and create two field definitions that are ready for inclusion and modification in dialogs either via extends or via export to yaml.

Changes
-----------------

2.0
- Compatiblity with Magnolia 5.5
- Output warning when trying to retrieve incorrectly configured field values

1.0.2
- Compatiblity with Java 7 (by popular demand)

1.0.1
- Compatibility with Magnolia 5.4.5/magnolia-groovy-2.4.3  (due to MGNLGROOVY-142)

1.0
- Initial release.

