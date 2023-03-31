javac -d . Url.java
javac -d . Fonction.java
javac -d . Utilitaire.java
javac -d . Mapping.java
javac -d . TestAnnoter.java
javac -d . FrontServlet.java
jar cvfm framework.jar Manifest.txt etu1804/framework/servlet/FrontServlet.class etu1804/framework/Mapping.class annote/Url.class fonction/Fonction.class fonction/Utilitaire.class us/TestAnnoter.class
jar cvfm framework.war
