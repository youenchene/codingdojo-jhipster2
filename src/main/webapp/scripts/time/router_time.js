'use strict';

simplecraApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/time', {
                    templateUrl: 'views/times.html',
                    controller: 'TimeController',
                    resolve:{
                        resolvedTime: ['Time', function (Time) {
                            return Time.query().$promise;
                        }],
                        resolvedProject: ['Project', function (Project) {
                            return Project.query().$promise;
                        }],
                        resolvedUser: ['User', function (User) {
                            return User.query().$promise;
                        }]
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.all]
                    }
                })
        });
