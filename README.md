# Zed-Attack-Proxy - Workshop

#Requisites:
	 Java
	 Gradle
	 Dowload ZAP proxy for your OS
	 Firefox browser
	 Mutillidae app running in virtual box to run tests against
 
 #Steps:
	 In firefox,set proxy to localhost:8080
	 Start ZAP in daemon mode 
	 Run tests : ``` gradlew clean test```
	 Reports will be generated in 'scan-results' folder
	 
