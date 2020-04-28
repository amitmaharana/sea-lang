# SEA Programming Language

![alt text](https://raw.githubusercontent.com/amitmaharana/amitmaharana.github.io/master/images/sample_sealang.png)

### Contributors
* Amit Maharana
* Eric Wegener
* Shubham Bhardwaj
* Shwetank Bhardwaj

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
* Clone this repository and go to the project directory 
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
[YouTube Link](https://www.youtube.com/watch?v=CPQTNG_H11o)

### Syntax

#### Declaration
```
Int a;
Bool b;
String c;
Int[] d;
Bool[] e;
String[] f;
```

#### Assign
```
a = 2;
b = False;
c = "string";
d = [1, 2, 3];
e = [True, False];
f = ["one", "two", "three"];
d[2] = 5;
d[a] = 3;
d[a - 1] = 1;
```

#### Expressions
```
(5 + d[1]) / 4 * 3 - a
Integer.toString(4 + 3)
```

#### Conditons
```
((2 > 3) || !(5 == a)) && c.equals("abc")
Boolean.toString(5 != 3)
```

#### String Operations
```
s.concat("abc")
s.split("-")
s.substring(2)
s.substring(1, 4)
s.length()
```
<sup><sup>split doesnt recognize variables: s.split(c) is not supported</sup></sup>

#### Array Properties
```
d.length
```
<sup><sup>output of length operation needs to be assigned to variable first</sup></sup>

#### If - Else
```
if (a > b) {
  show a;
} else {
  show b;
}
```

#### Ternary Expression
```
c = (a > b) ? a : b;
```

#### For Loop
```
for(Int i = 0; i < 5; i++) {
  show i;
}

for(i = 10; i > 5; i--) {
  show i;
}

for(Int i = 0; i < 5; i = i + 2) {
  show i;
}
```

#### While Loop
```
a = 0;
while (a < 10) {
  show a;
  a = a + 2;
}
```

#### For Range Loop
```
for a ++ in (1, 10) {
  show a;
}

for b -- in (10, 1) {
  show b;
}
```

#### Show
```
show a;
show 2 * (3 - 1) ;
show d[a];
show "abc".concat("def");
```
