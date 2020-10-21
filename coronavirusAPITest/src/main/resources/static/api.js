function getCountry(name,callback) {
    var settings = {
    	"async": true,
    	"crossDomain": true,
    	"url": "https://rapidapi.p.rapidapi.com/name/"+name,
    	"method": "GET",
    	"headers": {
    		"x-rapidapi-host": "restcountries-v1.p.rapidapi.com",
    		"x-rapidapi-key": "361b123075msh74943d4e9748c13p1053c6jsn7c3d22d2946a"
    	}
    };

    $.ajax(settings).done(function (response) {
    	console.log(response);
    });
}