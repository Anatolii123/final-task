var statisticsApi = Vue.resource('/statistics');

Vue.component('statistics-list', {
    props: ['statistics', 'statisticsAttr', 'showTable'],
    data: function () {
        return {
            personcount: '',
            carcount: '',
            uniquevendorcount: ''
        }
    },
    watch: {
        statisticsAttr: function (newVal) {
            this.personcount = newVal.personcount;
            this.carcount = newVal.carcount;
            this.uniquevendorcount = newVal.uniquevendorcount;
        }
    },
    template:
        '<div align="center" style="width: 800px">' +
        '<table class="table" align="center" v-if="showTable" style="text-align: center">\n' +
        '  <thead>\n' +
        '    <tr>\n' +
        '      <th>Number of persons</th>\n' +
        '      <th>Number of cars</th>\n' +
        '      <th>Number of unique vendors</th>\n' +
        '    </tr>\n' +
        '  </thead>\n' +
        '  <tbody>\n' +
        '    <tr>\n' +
        '      <td>{{statistics.personcount}}</td>\n' +
        '      <td>{{statistics.carcount}}</td>\n' +
        '      <td>{{statistics.uniquevendorcount}}</td>\n' +
        '    </tr>\n' +
        '  </tbody>\n' +
        '</table>' +
        '<input type="button" class="btn btn-outline-primary" value="Show statistics" @click="show"/>' +
        '<input type="button" class="btn btn-outline-primary" value="Hide statistics" @click="hide" style="margin-left: 5px"/>' +
        '<input type="button" class="btn btn-outline-primary" value="Clear all" @click="clear" style="margin-left: 5px"/>' +
        '</div>',
    created: function () {
        statisticsApi.get().then(result =>
            result.json().then(data =>
                this.statistics = data
            )
        )
    },
    methods: {
        show: function () {
            this.showTable = true;
            this.$http.get('http://localhost:8080/statistics').then(response =>
                response.json().then(data =>
                    this.statistics = data
            ));
        },
        hide: function () {
            this.showTable = false;
        },
        clear: function () {
            this.$http.get('http://localhost:8080/clear').then(response =>
                console.log(response)
            );
        }
    }
});

var app = new Vue({
    el: '#app4',
    template: '<div align="center"><statistics-list :statistics="statistics" /></div>',
    data: {
        showTable: false,
        statistics: []
    }
});