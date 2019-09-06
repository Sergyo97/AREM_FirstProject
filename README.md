
# Web Server (Apache)

Server capable of delivering HTML pages and PNG type images, where an IoC framework is provided for the construction of web applications from POJOs, attending multiple non concurrent requests.

## Getting Started
These instructions will help you obtain a copy of the project and run it on your local machine for development and testing purposes.

### Prerequisites

 - Maven 3.6. If you don't have it, follow this tutorial according to your OS. [How to install maven](https://maven.apache.org/install.html)
 - JDK (Java Development Kit) 1.8. If you don't have it, go to this tutorial. [Java SE Development Kit 8](https://www.oracle.com/java/technologies/jdk8-downloads.html)
 - Git bash. If you don't have it, go here [GIT](https://git-scm.com/) and follow this basic turorial. [GIT DOCS](https://git-scm.com/docs)

### Installation and Local Deployment
To get the project run this command from the command terminal of your choice.

    git clone https://github.com/Sergyo97/AREM_FirstProject

First, go to the project directory.

    cd "folder where you cloned it"

To run the whole project.

    mvn package
    mvn exec:java -D exec.mainClass="edu.escuelaing.arem.firstproject.Controller"
    
### Web Deployment
This project is deployed under Heroku

[![Servidor Apache](https://www.herokucdn.com/deploy/button.png)](https://sergios-framework.herokuapp.com)

### Use of POJOs applications 

    https://sergios-framework.herokuapp.com/apps/test
    https://sergios-framework.herokuapp.com/apps/add?n=1&n=2
    
### Obtaining Resources
    https://sergios-framework.herokuapp.com/git.jpg
    https://sergios-framework.herokuapp.com/test.html
    

## Built with
[Maven](https://maven.apache.org/) - Dependency Management

## Author
Sergio Hernando Ruiz Paez - [GitHub](https://github.com/Sergyo97) - Escuela Colombiana de Ingeniería Julio Garavito

## License
This project is under GNU General Public License - see  [LICENSE](https://github.com/Sergyo97/AREM_FirstProject/blob/master/LICENSE) to more info.
