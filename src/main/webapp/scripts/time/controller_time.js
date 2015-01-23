'use strict';

simplecraApp.controller('TimeController', function ($scope, resolvedTime, Time, resolvedProject, resolvedUser) {

        $scope.times = resolvedTime;
        $scope.projects = resolvedProject;
        $scope.users = resolvedUser;

        $scope.create = function () {
            Time.save($scope.time,
                function () {
                    $scope.times = Time.query();
                    $('#saveTimeModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.time = Time.get({id: id});
            $('#saveTimeModal').modal('show');
        };

        $scope.delete = function (id) {
            Time.delete({id: id},
                function () {
                    $scope.times = Time.query();
                });
        };

        $scope.clear = function () {
            $scope.time = {date: null, time: null, id: null};
        };
    });
