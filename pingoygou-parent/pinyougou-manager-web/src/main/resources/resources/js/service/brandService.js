//brand的服务层实现
app.service("brandService",function ($http) {
    this.findall=function () {
        return $http.get("/brand/findAll.do");
    }
    this.findPage=function (pagenum,size) {
        return $http.get("/brand/findPage.do?page="+pagenum+"&size="+size);
    }
    this.deletes=function (ids) {
        return $http.get("/brand/deletes.do?ids="+ids);
    }
    this.search=function (pagenum,size,searchEntity) {
        return $http.post("/brand/search.do?page="+pagenum+"&size="+size,searchEntity);
    }
    this.add=function (entity) {
        return $http.post("/brand/add.do",entity);
    }
    this.update=function (entity) {
        return $http.post("/brand/update.do",entity);
    }
    this.findOne=function (id) {
        return $http.get("/brand/findOne.do?id="+id);
    }
});