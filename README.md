# SEA Programming Language

### Build System
* Compiler - Windows
* Runtime - Windows

### Tools Used
* ANTLR 4.8
* Java 8

### Instructions to Install
* Download JDK from [here](https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html)
* Download Apache Maven from [here](https://maven.apache.org/download.cgi)
* Add path variables to environment as shown [here](https://mkyong.com/maven/how-to-install-maven-in-windows/)
* Clone this repository and go to project directory 
```
git clone https://github.com/amitmaharana/SER502-Spring2020-Team1.git
cd SER502-Spring2020-Team1
```

### Instructions to Build and Run

#### 1. Build the Compiler
```
mvn clean package
```

#### 2. Run the Runtime
```
java -jar target/SEALang.jar data/filename.sea
```

### Demo Video
```
placeholder
```

### Contributors
* Amit Maharana
* Eric Wegener
* Shubham Bhardwaj
* Shwetank Bhardwaj
