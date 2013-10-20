Cover Finder
=========

Cover finder is a java library for get a music cover from cover services, the library can iterate over diferent cover services searching the cover.
  - Search over diferent cover services
  - Add other cover services
    


How its works
-----------

Search a cover:

``` java
//Define the services to use

ICoverService lastFM =  new LastFmCoverService("your-api-key","your-secret");
ICoverService iTunes = new ITunesCoverService();

//Add the services to the invoquer
InvokerService invoquer = new InvokerService();
invoquer.addCoverService(lastFM);
invoquer.addCoverService(iTunes);

//Perfom the seearch
CoverResult cover = invoquer.execute("Master of puppets","Metallica");
```

If the invoquer not found the cover in the first service it will search in the second service and successively.

Providers
-----------

The providers use the cover service to get the cover in a especific format or using some extra funcionallity, you can create your own providers and using it in your especific applications. For instance mi application need a input stream of the cover and if the invoquer have not cover i need a default image,there is a example in the Using a provider secction

Using a provider
-----

You can use a provider to get the cover in expecific format, by default the input stream provider is included, this provider use a UrlHandler to get a inputStream from url and a folderHandler to get a image from a local folder:

``` java
 //Define objects

        //Define all services

        ICoverService lastFM =  new LastFmCoverService("your-api-key","your-secret");       
        ICoverService iTunes = new ITunesCoverService();

        //Create invoker

        InvokerService invoquer = new InvokerService();

        invoquer.addCoverService(lastFM);
        invoquer.addCoverService(iTunes);

        //Create handler
        
        URLImageHandler urlHandler = new URLImageHandler();
        CustomFolderImageHandler customFolderHandler = new CustomFolderImageHandler();


        InputStreamCoverProvider coverProvider = new InputStreamCoverProvider(invoquer,urlHandler,customFolderHandler,"testFolder/test.png",InputStreamCoverProvider.MEDIUM_SIZE);


        InputStream imageStream = coverProvider.getCover("Fake name","Fake Artist");

```

Create your own provider
-----

You can create your own provider implementing the **ICoverProvider**

Create yout own Cover services
-----

By default Last FM and ITunes service are implemented but you can add new cover services implementing the **ICoverService**

More documentation
----
For extra examples of use you can see the test.
