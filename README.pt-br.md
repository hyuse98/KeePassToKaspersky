<p align="right">
    <a href="./README.pt-br.md">Português (Brasil)</a> |
    <a href="./README.md">English</a>
</p>

# KeePassToKaspersky

Parser CSV 1:1 do software KeePassXC para Kaspersky Password Manager

### Conteudo
 * [O Problema](#o-problema)
 * [A Solução](#a-solução)
 * [Como Usar](#como-usar)
   * [Requisitos](#requisitos)
   * [Instruções](#instruções)
   * [Build](#build)

## O Problema

Migrar credenciais do KeePassXC para o Kaspersky Password Manager através de arquivos CSV é um processo que pode resultar em falhas e perdas de dados durante a importação.
O KPM possui um parser de CSV extremamente rígido que não consegue lidar de forma nativa com a exportação bruta do KeePassXC em alguns casos.
Ao tentar importar diretamente alguns problemas podem impedir que alguns registros sejam concluídos

- Quebra de Parser por Vírgulas: Senhas fortes geradas pelo KeePassXC frequentemente contêm vírgulas, como o formato CSV separa valores por vírgulas, o importador do Kaspersky Password Manager se perde na leitura das colunas e simplesmente descarta silenciosamente a credencial inteira, sem avisar que ocorreu um problema.

- Falha por Quebras de Linha: O KeePassXC permite anotações (Notes) com múltiplas linhas. O KPM quebra a leitura sequencial quando encontra um \n (Enter) num campo de texto, corrompendo as linhas subsequentes do arquivo.

- Ausência de URL: KeePassXC não obriga as entradas ter a URL como obrigatório, no Kaspersky Password Manager é um campo obrigatório, pois ele usa a URL como chave primaria e agrupar credenciais pela a mesma, ao exportar uma entrada do KeePassXC sem URL ela será ignorada pelo Kaspersky Password Manager.

## A Solução

Este software atua como um middleware de sanitização, ele processa o arquivo CSV bruto do KeePassXC e gera um arquivo formatado para o Kaspersky, resolvendo todos os problemas de normalização dos dados.

O KeePassXC exporta os dados para CSV com a seguinte estrutura

````CSV
"Group","Title","Username","Password","URL","Notes","TOTP","Icon","Last Modified","Created"
"Raiz/diretorio","Gmail","exemple@gmail.com","2N*'u_;_4S:VK+^z","https://mail.google.com/","","","0","2026-01-01T19:37:00Z","2026-01-01T18:11:40Z"
````

O Kaspersky Password Manager Requer uma estrutura totalmente diferente especificada na documentação https://support.kaspersky.com.br/kpm-for-windows/26.0/130515

````CSV
url,username,password,name,extra
"https://mail.google.com/","exemple@gmail.com","2N*'u_;_4S:VK+^z","Gmail",""
````
O mapeamento 1:1 do arquivo CSV é relacionado da seguinte maneira

| Origem (KeePass) | Destino (Kaspersky) |
|:-----------------|:--------------------|
| URL              | url                 |
| Username         | username            |
| Password         | password            |
| Title            | name                |
| Notes            | extra               |

Para garantir algumas nuances além da estrutura do header do CSV, temos que garantir um padrão nos dados também, o algoritmo também trata os dados para evitar erros

- Garantir que todos os campos fiquem entre aspas duplas (""), para que o KPM leia senhas com vírgulas corretamente.
- Sanitiza quebras de linha (\n e \r) nas anotações, transformando-as em divisores seguros.
- Injeta identificadores únicos baseados no "Title" para criar URLs Dummies nas entradas órfãs (sem URL).

## Como Usar

### Requisitos

- Java25+

### Instruções

Coloque o KeePassToKaspersky-v1.jar e o arquivo exportado do KeePassXC nas mesma pasta, o arquivo deve esta com o nome ````keepass_export.csv````
````
Pasta/
├── KeePassToKaspersky-v1.jar
└── keepass_export.csv
````

Abre o cmd ou terminal da sua preferência na pasta e execute o jar com a linha de comando abaixo
````bash
 java -jar KeePassToKaspersky-V1.jar
````

Se tudo ocorrer sem problemas ira ter como saida um novo arquivo chamado ````kaspersky_ready.csv````

Para sua segurança após o uso é altamente recomendado apagar permanentemente ambos arquivos CSV!!!

### Build

Se você quiser fazer a build por sua conta use o comando abaixo ou usar ferramentas gradle na sua IDE

````bash
 ./gradlew build
````