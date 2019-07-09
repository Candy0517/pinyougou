app.controller("loginController",function ($scope,loginService,$controller) {

    $controller('baseController',{$scope:$scope});//继承

    $scope.showLoginName=function () {
        loginService.loginName().success(function (data) {
            $scope.loginName=data.loginName;
        })
    }

});