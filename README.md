This app shows an example using **Google Paging library v3** in a MVVM architecture design using some Google Architecture components (**ViewModel, LiveData**),
**Databinding**, dependency injection (**koin**), **Retrofit**, **Glide**. Besides it, the project shows examples of local tests (**Junit, Roboletric**) 
and instrumentation tests (**Espresso, Ui Automator**).

The scenario where the app was constructed is described as following.

Using the Github REST search API the app get some information about repositories written in kotlin and show those info on a infinite list. Because the app uses
ViewModel to maintain the kotlin repository list, the app survive to device configuration changes maitaining your before state, without seek to Internet again.
The app too makes local cache (in disk) of the requests during a defined period of time.
