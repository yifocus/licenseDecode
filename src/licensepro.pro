-injars licenseDecode.jar
-outjars licenseDecodeout.jar

-libraryjars 'G:\jhStudio\workspace1\decodeLicense\src\commons-logging.jar'
-libraryjars 'G:\jhStudio\workspace1\decodeLicense\src\fastjson-1.2.3.jar'
-libraryjars 'G:\jhStudio\workspace1\decodeLicense\src\guava-16.0.1.jar'
-libraryjars 'G:\jhStudio\workspace1\decodeLicense\src\log4j-1.2.17.jar'
-libraryjars 'G:\jhStudio\workspace1\decodeLicense\src\slf4j-api-1.6.1.jar'
-libraryjars 'G:\jhStudio\workspace1\decodeLicense\src\slf4j-jcl-1.6.1.jar'
-libraryjars 'G:\jhStudio\java\jdk1.7.0_76\jre\lib\rt.jar'
-libraryjars 'G:\jhStudio\java\jdk1.7.0_76\jre\lib\javaws.jar'
-libraryjars 'G:\jhStudio\java\jdk1.7.0_76\jre\lib\deploy.jar'
-libraryjars 'G:\jhStudio\java\jdk1.7.0_76\jre\lib\jce.jar'
-libraryjars 'G:\jhStudio\java\jdk1.7.0_76\jre\lib\jfr.jar'
-libraryjars 'G:\jhStudio\java\jdk1.7.0_76\jre\lib\jfxrt.jar'
-libraryjars 'G:\jhStudio\java\jdk1.7.0_76\jre\lib\jsse.jar'

-dontshrink
-dontoptimize
-dontusemixedcaseclassnames





-if public class *

-keep class com.ejiahe.eim.focus.lisence.util.LicenseUtil {
	*;
}
-keep class com.ejiahe.eim.focus.lisence.conf.*{
	*;
}

-keep enum com.ejiahe.eim.focus.lisence.conf.*{
 *;
}


-keep class com.ejiahe.eim.focus.lisence.manager.**{
    *;
}

-keep class * extends com.ejiahe.eim.focus.lisence.constant {
    *;
}



-keep class com.ejiahe.eim.focus.lisence.entity.*{
	*;
}

-keep class com.ejiahe.eim.focus.lisence.util.MacAddressAPI{
  *;
}

# Also keep - Enumerations. Keep the special static methods that are required in
# enumeration classes.
-keepclassmembers enum  * {
    *;
}

# Also keep - Database drivers. Keep all implementations of java.sql.Driver.
-keep class * extends java.sql.Driver

# Also keep - Swing UI L&F. Keep all extensions of javax.swing.plaf.ComponentUI,
# along with the special 'createUI' method.
-keep class * extends javax.swing.plaf.ComponentUI {
    public static javax.swing.plaf.ComponentUI createUI(javax.swing.JComponent);
}

# Keep - Native method names. Keep all native class/method names.
-keepclasseswithmembers,includedescriptorclasses,allowshrinking class * {
    native <methods>;
}
