var carApi = Vue.resource('/cars');

Vue.component('car-row', {
    props: ['car'],
    template: '<tr><th scope="row">{{car.id}}</th><td>{{car.model}}</td><td>{{car.horsepower}}</td><td>{{car.ownerId}}</td></tr>'
});

Vue.component('car-form', {
    props: ['cars', 'carAttr'],
    data: function () {
        return {
            model: '',
            horsepower: '',
            ownerId: '',
            id: ''
        }
    },
    watch: {
        personAttr: function (newVal, oldVal) {
            this.model = newVal.model;
            this.horsepower = newVal.horsepower;
            this.ownerId = newVal.ownerId;
            this.id = newVal.id;
        }
    },
    template:
        '<div align="center">' +
        '<input type="text" class="form-control" placeholder="Write model (e.g., BMW-X5)" v-model="model"/><br>' +
        '<input type="text" class="form-control" placeholder="Write horsepower" v-model="horsepower"/><br>' +
        '<input type="text" class="form-control" placeholder="Write owner ID" v-model="ownerId"/><br>' +
        '<input type="button" class="btn btn-outline-primary" value="Save" @click="save"/><br>' +
        '</div>',
    methods: {
        save: function () {
            var car = {id: 5, model: this.model, horsepower: this.horsepower, ownerId: this.ownerId};
            this.$http.post('http://localhost:8080/2/car', car).then(response => {
                this.cars.push(response.body);
            });
            this.model = '';
            this.horsepower = '';
            this.ownerId = '';
        }
    }
});

Vue.component('cars-list', {
    props: ['cars'],
    template:
        '<div align="center" style="width: 400px;">' +
        '<car-form :cars="cars" :carAttr="car"/><br>' +
        '<table class="table" align="center" style="text-align: center">\n' +
        '  <thead>\n' +
        '    <tr>\n' +
        '      <th>ID</th>\n' +
        '      <th>Model</th>\n' +
        '      <th>Horsepower</th>\n' +
        '      <th>Owner ID</th>\n' +
        '    </tr>\n' +
        '  </thead>\n' +
        '  <tbody>\n' +
        '<car-row v-for="car in cars" :key="car.id" :car="car"/>' +
        '  </tbody>\n' +
        '</table>' +
        '</div>',
    created: function () {
        carApi.get().then(result =>
            result.json().then(data =>
                data.forEach(car => this.cars.push(car))
            )
        )
    }
});

var app = new Vue({
    el: '#app2',
    template: '<div align="center">' +
        '<cars-list :cars="cars" />' +
        '<personas-list :personas="personas" />' +
        '</div>',
    data: {
        cars: [],
        personas: []
    }
});