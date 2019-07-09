app.controller("brandController", function ($scope, $controller,brandService) {

    $controller('baseController',{$scope:$scope});//继承

    //查询全部
    $scope.findAll = function () {

        brandService.success(function (data) {

            $scope.list = data;

        })
    };

    //分页
    $scope.findPage = function (page, size) {
        brandService.findPage(page, size).success(function (data) {
            $scope.list = data.rows;
            $scope.paginationConf.totalItems = data.total;
        })
    };



    //增加或者修改
    $scope.save = function () {
        brandService.save($scope.entity).success(function (data) {
            if (data.success) {
                alert(data.message);
                $scope.reloadList();
            } else {
                alert(data.message);
            }
        })
    };

    //查找一个
    $scope.findOne = function (id) {
        brandService.findOne(id).success(function (data) {
            $scope.entity = data;
        })
    };
    //删除
    $scope.dele = function () {
        brandService.dele( $scope.selectIds).success(function (data) {
            if (data.success) {
                alert(data.message);
                $scope.reloadList();
            } else {
                alert(data.message);
            }
        })
    };
    //模糊查询
    $scope.searchEntity={};

    $scope.search=function (page,size) {
        brandService.search(page,size,$scope.searchEntity).success(function (data) {
            $scope.list=data.rows;
            $scope.paginationConf.totalItems=data.total;
        })
    };

   // $scope.page= $scope.paginationConf.currentPage;
   // $scope.size=$scope.paginationConf.itemsPerPage;

});