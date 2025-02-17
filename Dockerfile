# Usa uma imagem do OpenJDK 21 como base
FROM openjdk:21-jdk-slim

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia o arquivo JAR do backend para o container
COPY target/backend-0.0.1-SNAPSHOT.jar app.jar

# Expor a porta 8080 para comunicação
EXPOSE 8080

# Comando para rodar a aplicação Spring Boot
CMD ["java", "-jar", "app.jar"]