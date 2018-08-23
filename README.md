# booksApi

## Execução

- Para executar o projeto, é preciso antes verificar se o PostgreSQL está executando. A url de conexão do projeto com o PostgreSQL fica no arquivo config/config.json;

- No projeto, na pasta database, há dois scripts SQL, um para criar a tabela que a aplicação consulta e outro para alterar a senha do usuário postgres para a senha que vem por padrão no arquivo de configuração;

- Caso queira utilizar outra porta ou outro usuário/senha, não se esquecer de alterar o arquivo config.json;

- Para rodar os testes integrados, execute o comando ```./gradlew clean build integrationTests```

- Para criar o jar da aplicação, execute o comando ```./gradlew clean readyToRun```. O comando criará o diretório run, com o jar e o arquivo de configuração.

- Para executar a aplicação, entre no diretório run, e execute o comando ```java -jar booksApi.jar```

- Existem opções de execução para deixar a consulta no crawler mais rápida, cacheando os resultados em memória após consultados. Para isso basta setar no arquivo config.json as opções cache ou startupCache como true. A diferença é que a opção cache fará o cache somente após a primeira execução, enquanto a segunda o fará ao subir a aplicação. Caso as duas sejam true, prevalecerá a opção na subida.

**Caso queira executar o jar sem entrar na pasta run, lembre-se de no diretório corrente possuir o arquivo config/config.json**
