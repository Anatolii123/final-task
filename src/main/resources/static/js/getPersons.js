Vue.component('todo-item', {
    template: '<li>Это одна задача в списке</li>'
})

var app = new Vue({
    el: '#app2',
    data: {
        personId: 'Привет!'
    }
});