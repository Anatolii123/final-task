var idApi = Vue.resource('/persons');

Vue.component('id-row', {
    props: ['id'],
    template: '<option value="{{id}}">{{id}}</option>'
});

Vue.component('ids-list', {
    props: ['ids'],
    template:
        '<div align="center" style="width: 400px;">' +
        '   <select name="ids">' +
        '       <id-row v-for="id in ids" :key="id" :id="id"/>' +
        '   </select>' +
        '</div>',
    created: function () {
        idApi.get().then(result =>
            result.json().then(data =>
                data.forEach(id => this.ids.push(id))
            )
        )
    }
});

var app = new Vue({
    el: '#app3',
    template: '<div align="center"><ids-list :ids="ids" /></div>',
    data: {
        ids: []
    }
});