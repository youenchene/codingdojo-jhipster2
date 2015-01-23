'use strict';

simplecraApp.factory('Time', function ($resource) {
        return $resource('app/rest/times/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    });


simplecraApp.factory('User', function ($resource) {
    return $resource('app/rest/users/:id', {}, {
        'query': { method: 'GET', isArray: true},
        'get': { method: 'GET'}
    });
});
