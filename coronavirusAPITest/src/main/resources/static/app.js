var app = (function () {
    function find(apielement) {
        var name = $(apielement).find("td:first-child").text();
        var deaths = $(apielement).find("td:nth-child(2)").text();
        var confirmed = $(apielement).find("td:nth-child(3)").text();
        var recovered = $(apielement).find("td:nth-child(4)").text();
        $("#country").text(name);
        var table = document.getElementById("tableSpecifications");
        table.style.display = "block";
        $("#specifications").empty();
        var row = "<tr> <td> Deaths</td> <td>"+deaths+"</td> </tr>";
        $("#specifications").append(row);
        row = "<tr> <td> Infected</td> <td>"+confirmed+"</td> </tr>";
        $("#specifications").append(row);
        row = "<tr> <td> Cured</td> <td>"+recovered+"</td> </tr>";
        $("#specifications").append(row);
        map(name);
        getByName(name);
    }

    function map(name){
        var url = window.location;
        var urlNew = url.protocol+"//"+url.host + "/coronavirus/coordinates/"+name;
        var getPromise = $.get(urlNew);
        getPromise.then(
            function(data){
                var info = JSON.parse(data);
                plotMarkers(info[0].latlng);
            },
            function(){
                console.log('error')
            }
        );
        return getPromise;
    }

    function showTableProvinces(data){
        var table = document.getElementById("tableProvinces");
        table.style.display = "block";
        $("#provinces").empty();
        data.map(function(element){
            var markup = "<tr> <td>"+ element.province +"</td> <td>"+element.deaths+"</td> <td>"+ element.confirmed +"</td> <td>"+ element.recovered +"</td> </tr>";
            $("#provinces").append(markup)
        });
    }

    function table_Countries(data){
        $("#countries").empty();
        data.map(function(element){
            var markup = "<tr onclick=app.find(this)> <td>"+ element.country +"</td> <td>"+element.deaths+"</td> <td>"+ element.confirmed +"</td> <td>"+ element.recovered +"</td> </tr>";
            $("#countries").append(markup)
        });
    }

    function getByName(name) {
        var url = window.location;
        var urlNew = url.protocol+"//"+url.host + "/coronavirus/"+name;
        var getPromise = $.get(urlNew);
        getPromise.then(
            function(data){
                var info = JSON.parse(data);
                showTableProvinces(info.data.covid19Stats);
            },
            function(){
                console.log('error')
            }
        );
        return getPromise;
    }

    function getAll(callback) {
        var url = window.location;
        var urlNew = url.protocol+"//"+url.host + "/coronavirus" + "/all";
        var getPromise = $.get(urlNew);
        getPromise.then(
            function(data){
                callback(JSON.parse(data));
            },
            function(){
                console.log('error')
            }
        );
        return getPromise;
    }

    return {
        init: function () {
            initMap();
        },
        showAll:getAll(table_Countries),
        find:find
    }
})();