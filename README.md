## Java Client 

Componente para simplificar o consumo das apis disponibilizadas no [APIGov](https://apigov.serpro.gov.br/);

## Requisitos

 * Java (7+)
 * Maven (3+)
 
## Exemplo de consumo de API

```java
    ApigovSDK sternaAPI = new ApigovSDK("tokenDeAcesso", "uri");	
    Response response = sternaAPI.get("/catalogo/categorias").request();
    MeuDTO dto = response.readEntity(MeuDTO.class);
```

## Obter token de acesso

```java
    Token token = TokenAPI.getValidToken("consumerKey", "consumerSecret");
```

> Atenção: os dados de ConsumerKey e ConsumerSecret não devem ficar expostos em código cliente.
