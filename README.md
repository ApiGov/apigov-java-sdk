## Java Client 

Componente para simplificar o consumo das apis disponibilizadas no [APIGov](https://apigov.serpro.gov.br/);

## Requisitos

 * Java (7+)
 * Maven (3+)

## Instalação

Basta adicionar a dependencia em seu projeto:

```xml
<dependency>
    <groupId>br.gov.serpro</groupId>
    <artifactId>apigateway.apigov-java-sdk</artifactId>
    <version>0.0.1</version>
</dependency>
```
 
 
## Exemplo

Para consumir uma api, basta informar o token de acesso e a uri da api. Em seguida escolher o método http desejado e informar o path.

```java
    ApigovSDK sternaAPI = new ApigovSDK("tokenDeAcesso", "uri");	
    Response response = sternaAPI.get("/catalogo/categorias").request();
    MeuDTO dto = response.readEntity(MeuDTO.class);
```

Em alguns casos, será interessante obter o token de acesso dinâmicamente, para isto é preciso informar as credenciais: ConsumerKey e ConsumerSecret.

```java
    Token token = TokenAPI.getValidToken("consumerKey", "consumerSecret");
```

> Atenção: os dados de ConsumerKey e ConsumerSecret não devem ficar expostos em código cliente.
