Vue.component('pwcs-list', {
    props: ['pwcs', 'pwcsAttr', 'showTable'],
    data: function () {
        return {
            name: '',
            birthDate: '',
            id: '',
            cars: ''
        }
    },
    watch: {
        pwcsAttr: function (newVal) {
            this.name = newVal.name;
            this.birthDate = newVal.birthDate;
            this.id = newVal.id;
            this.cars = newVal.cars
        }
    },
    template:
        '<div align="center" style="width: 800px">' +
        '<table class="table" align="center" v-if="showTable" style="text-align: center">\n' +
        '  <thead>\n' +
        '    <tr>\n' +
        '      <th>ID</th>\n' +
        '      <th>Name</th>\n' +
        '      <th>Birthday</th>\n' +
        '      <th>Car ID</th>\n' +
        '      <th>Car model</th>\n' +
        '      <th>Car horsepower</th>\n' +
        '    </tr>\n' +
        '  </thead>\n' +
        '  <tbody>\n' +
        '    <tr>\n' +
        '      <th>{{statistics.personcount}}</th>\n' +
        '      <th>{{statistics.carcount}}</th>\n' +
        '      <th>{{statistics.uniquevendorcount}}</th>\n' +
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
            this.$http.get('http://localhost:8080/personwithcars?personid=1').then(response =>
                response.json().then(data =>
                    this.pwcs = data
                ));
        },
        hide: function () {
            this.showTable = false;
        },
    }
});

var app = new Vue({
    el: '#app5',
    template: '<div align="center"><pwcs-list :pwcs="pwcs" /></div>',
    data: {
        showTable: false,
        pwcs: []
    }
});