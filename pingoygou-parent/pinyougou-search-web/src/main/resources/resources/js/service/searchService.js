app.service("searchService",function ($http) {

    this.searchItem=function (searchMap) {
        return $http.post("/search/itemSearch.do",searchMap);
    }

});