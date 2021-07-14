# Wishlist
## Descrição:

Esse projeto é uma API que representa um sistema de 'Lista de Favoritos'

## Índice
* [Pré-Requisitos](#pré-requisitos)
* [Para começar](#para-começar)
* [Compilando e Rodando o projeto](#compilando-e-rodando-o-projeto)
* [API](#api)
    * [User/Customer](#users-path)
    * [Wishlist](#wishlist-path)


## Pré-Requisitos:
* [Java 11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
  
* Instalação do maven (Linux):

  `sudo apt update`

  `sudo apt install maven`

* Instalação do mysql (Linux):

  `sudo apt update`

  `sudo apt install mysql-server`

* Instalação do maven (Windows):

    * [Maven](https://maven.apache.org/download.cgi)

* Instalação do mysql (Windows):

    * [MySql](https://www.mysql.com/downloads/)

## Para começar:
* Clone este repositório:

    SSH:`git clone git@github.com:edithsoares/challenge-api.git`
    ou
    Https: `https://github.com/edithsoares/challenge-api.git`
    
* Crie um banco de dados no mysql com o nome `challenge`:


  `CREATE DATABASE challenge;`

* Substitua as credenciais para login no arquivo application.properties:

    * Substitua nas linhas 4 e 5 (username e password) pela suas credenciais do banco de dados.

      ***OBS:** Se você não definiu nenhum username diferente na instalação provavelmente ele deve ser `root` e não será necessária a substituição*
    

## Compilando e rodando o projeto
* Para compilar o projeto vá até a pasta onde se encontra o arquivo `pom.xml` e execute o comando:

  `mvn clean install`
* Se o resultado do build for `BUILD SUCCESS`, rode o projeto usando:

  `mvn exec:java`

    * Quando o projeto estiver rodando um log parecido com esse vai aparecer no terminal:

      Mude para o diretório onde pom.xml é criado por archetype: gere e execute o seguinte comando para compilar as fontes do seu aplicativo:

       `mvn mvn compile`

      Esse log indicará em que porta o servidor estará rodando, nesse caso, ele está rodando na porta `8080` e é para essa porta que devemos direcionar nossas requisições.

      `localhost:8080/{path_da_api_aqui}`

## API

### Users Path
* **Adicionar um usuário**

  `POST: api/users`

  **Json**
  `{
  "id": 5,
  "fullName": "edith",
  "email": "rtet",
  "password": "45"
  }`

  **Retorno:**
    * Se o usuário for adicionado:

      `status: 201`

      Retorna o user cadastrado.
      
    * Se o usúario já estiver casdastrado recebera um aviso, não permitindo recadastro com o mesmo email
    * Se o usuário não for adicionado:

    `status: 400`
  
    `{
      "success": false,
      "message": "Request Errorscom.luizalabs.challenge.exceptions.BusinessRuleErrors: Email já esta sendo utilizado em outra conta."
    }`
  
* **Ver um usuário**

  `GET: api/users/{:id}`

  **Params:**
    * id: id_do_usuario;
    
    **Retorno:**
    * Se o usuário existir:

      `status: 200`

      ```
      {
        "id": 2,
        "fullName": "edith",
        "email": "rtet",
        "password": "45"
        }
      ```

    * Se o usuário não existir:

      `status: 404`

      `{
      "success": false,
      "message": "user not found by id: 7"
      }`
      

* **Excluir um usuário**

  `DELETE: api/users/{:id}`

  **Params:**
    * id: id_do_usuario;
    
    **Retorno:**
    * Se o usuário for excluído:

      `status: 200`

      `{
      "success": true,
      "message": "User deleted id: 1"
      }`

    * Se o usuário não for excluído:

      `status: 400`

      `{
      "success": false,
      "message": "user not found by id: 3"
      }`

* **Atualizar um usuário**

  `PUT: api/users/update/{:id}`

  **Params:**
    * User + id
    
    `{
      "id": 2,
      "fullName": "edith",
      "email": "userr@email.com",
      "password": "45"
      }`
  

  **Retorno:**
    * Se o usuário for atualizado:
      `status: 200`

      `{
      "id": 2,
      "fullName": "edith",
      "email": "userr@email.com",
      "password": "45"
      }`

    * Se o usuário não for atualizado:

      `status: 404`

      `Errors not found`

### Wishlist Path
* **Adicionar na wishlist**

  `POST: api/wishlist`
    
    `{
        "user": {
        "fullName": "edith",
        "email": "rtet",
        "password": "45",
        "id": 2
        },
        "products":[ {
        "title": "errr novo",
        "price": 3540.00,
        "brand": "HyperX",
        "reviewScore": 1,
        "id": 2
        }]
    }`

  **Query Params:**
    * user_id: id_do_cliente;
    * product_id: id_do_produto;

  **Retorno:**
    * Se o produto for adicionado à lista:

      `status:201`

      retorna a wishlist com os dados adicionados
    
    `{
          "user": {
          "id": 2,
          "fullName": "edith",
          "email": "rtet",
          "password": "45"
          },
          "products": [{
          "id": 3,
          "title": "errr novo",
          "price": 3540.00,
          "brand": "HyperX",
          "reviewScore": 1
          }],
          "date": "14-07-2021 01:37:09",
          "totalPrice": null,
          "id": 5
      }`
      
    * Se o produto não for adicionado à lista:

      `status:400`
    

* **Ver a lista de favoritos**

  `GET: api/wishlist/{:email}`
    
    Retorna a lista completa
  
    `[
  {
      "id": 2,
      "title": "errr novo",
      "price": 3540.00,
      "brand": "HyperX",
      "reviewScore": 1
      },
      {
      "id": 3,
      "title": "errr nfddfovo",
      "price": 3540.00,
      "brand": "HyperX",
      "reviewScore": 1
      }, 
        {
      "id": 4,
      "title": "errr nfddfovo",
      "price": 3540.00,
      "brand": "HyperX",
      "reviewScore": 1
      }
  ]`

    **Ver item da lista de favoritos**
    **Query Params:**
  
    * email: id_do_cliente;
    * product_id: id_do_produto;
    
    **Retorno:**
    * Se o item existir:

      `status: 200`

      ```
      Item
      id: id_do_item, Cliente: id_do_cliente
      ```

    * Se o item não existir:

      `status: 404`

      `Produto não encontrado`

* **Excluir um item**

  `DELETE: api/wishlist/{:idUser}{:idProduto}`

    **Retorno:**
    * Se o item for excluído:

      `status: 200`

    * Se não for excluído:

      `status: 400`

## Contact

Edith Soares - tainajmedeiros@gmail.com

LinkedIn: [in/edith-soares/](https://www.linkedin.com/in/edith-soares/)

Project Link: [https://github.com/edithsoares/challenge-api](https://github.com/edithsoares/challenge-api)