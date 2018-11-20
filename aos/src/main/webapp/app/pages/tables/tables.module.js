/**
 * @author v.lugovsky
 * created on 16.12.2015
 */
(function () {
  'use strict';

  angular.module('BlurAdmin.pages.tables', [])
    .config(routeConfig);

  /** @ngInject */
  function routeConfig($stateProvider, $urlRouterProvider) {
    $stateProvider
        .state('basicdata', {
          url: '/basicdata',
          template : '<ui-view  autoscroll="true" autoscroll-body-top></ui-view>',
          abstract: true,
          controller: 'TablesPageCtrl',
          title: '基础数据管理',
          sidebarMeta: {
            icon: 'ion-grid',
            order: 0,
          },
        }).state('basicdata.basic', {
          url: '/basic',
          templateUrl: 'app/pages/tables/basic/tables.html',
          title: '机场数据',
          sidebarMeta: {
            order: 0,
          },
        });
    $urlRouterProvider.when('/basicdata','/basicdata/basic');
  }

})();
