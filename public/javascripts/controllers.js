var capuchinApp = angular.module('Capuchin', ['ngResource']).
    directive('myCurrentTime', function ($timeout, dateFilter) {
        return function (scope, element, attrs) {
            var format, timeoutId;

            function updateTime() {
                element.text(dateFilter(new Date(), format));
            }

            // watch the expression, and update the UI on change.
            scope.$watch(attrs.myCurrentTime, function (value) {
                format = value;
                updateTime();
            });

            // schedule update in one second
            function updateLater() {
                // save the timeoutId for canceling
                timeoutId = $timeout(function () {
                    updateTime(); // update DOM
                    updateLater(); // schedule another update
                }, 1000);
            }

            // listen on DOM destroy (removal) event, and cancel the next UI update
            // to prevent updating time ofter the DOM element was removed.
            element.bind('$destroy', function () {
                $timeout.cancel(timeoutId);
            });

            updateLater();
        }
    });

capuchinApp.controller('ApplicationController', function ($scope, $resource, $timeout) {
    $scope.name = "ApplicationController";
    var apps = $resource('/application/all');

    $scope.date = new Date();
    $scope.apps = [];

    function toArray(apps){
        appsArray = [];
        apps.forEach(function (item) {
            appsArray.push({"id": item.id, "name": item.name});
        })

        return appsArray;
    }

    function updateLater() {
        $timeout(function () {

            var aplications = [];
            applications = apps.query({}, function () {
                    $scope.apps = toArray(applications);

            })
            updateLater();

        }, 1000)
    }

    updateLater();

});