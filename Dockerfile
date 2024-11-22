FROM eclipse-temurin:17-jdk

# Criar e configurar o diretório de trabalho
WORKDIR /app

# Copiar os arquivos necessários para build e dependências do Gradle
COPY gradlew ./
COPY gradle ./gradle
COPY build.gradle ./
COPY settings.gradle ./

# Instalar permissões para o Gradle Wrapper
RUN chmod +x gradlew

# Baixar as dependências do Gradle
RUN ./gradlew dependencies --no-daemon

# Copiar o código-fonte
COPY src ./src

# Construir o projeto
RUN ./gradlew build --no-daemon

# Expôr a porta padrão da aplicação
EXPOSE 8080

# Comando para executar a aplicação
CMD ["./gradlew", "bootRun"]