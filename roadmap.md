In Quarkus there is many possibilities and ways of doing things
In this API i used PanasheEntityBase , I had some problems with PanasheEntity beceause it handls automaticly the ID
but i wanted to handle my custom ID


There is a way to use a more classic layer architecture here
we can create a DAO layer with an interface that implements PanasheRepository or PanasheRepositoryBase in case we want to handle our ID
we can create a Service layer and use @Inject to inject the DAO and finally we can inject the service in the controller layer


We can add also a security part to handle users , profiles and roles


