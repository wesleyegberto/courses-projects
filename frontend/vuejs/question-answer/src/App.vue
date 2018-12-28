<template>
  <div>
    <p>
      Ask a yes/no question:
      <input v-model="question">
    </p>
    <p>{{ answer }}</p>
    <div>
      <img v-if="showImage" :src="imageUrl">
    </div>
  </div>
</template>

<script>
const IMAGE_LOADING = 'https://loading.io/spinners/hourglass/lg.sandglass-time-loading-gif.gif';

export default {
  data: () => {
    return {
      question: '',
      showImage: false,
      answer: 'I cannot give you an answer until you ask a question!',
      imageUrl: null
    };
  },
  watch: {
    question: function (newQuestion, oldQuestion) {
      this.answer = 'Waiting for you to stop typing...';
      this.debouncedGetAnswer();
    }
  },
  created: function () {
    this.debouncedGetAnswer = _.debounce(this.getAnswer, 500);
  },
  methods: {
    getAnswer:  function () {
      if (this.question.indexOf('?') === -1) {
        this.answer = 'Questions usually contain a question mark. ;-)';
        return;
      }
      this.answer = 'Thinking...';
      this.imageUrl = IMAGE_LOADING;
      this.showImage = true;
      const vm = this;
      axios.get('https://yesno.wtf/api')
        .then(function (response) {
          vm.showImage = false;
          return response.data;
        })
        .then(function (result) {
          vm.answer = _.capitalize(result.answer);
          vm.imageUrl = result.image;
          vm.showImage = true;
        })
        .catch(function (error) {
          vm.answer = 'Error! Could not reach the API. ' + error;
        });
    }
  }
}
</script>

<style>
  #app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
    margin-top: 60px;
  }
  img {
    width: 300px
  }
</style>
