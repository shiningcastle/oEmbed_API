<template>
  <section>
    <!-- URL Input Box -->
    <div id="app" class="container is-max-desktop pt-5">
      <section class="pb-6 pt-6">
        <b-field label="oEmbed Test" large>
          <b-input
            type="url"
            v-model="url"
            size="is-large"
            expanded
            placeholder="Please input oEmbed URL"
          ></b-input>
          <br />
          <b-button
            type="is-primary"
            @click="getOembed"
            size="is-large"
            outlined
            >확인</b-button
          >
        </b-field>
      </section>
    </div>
    <!-- Youtube -->
    <div v-if="provider === 'www.youtube.com'" align="center">
      <table border="2" class="type07">
        <thead>
          <th align="center" id="chead">Item</th>
          <th align="center">Contents</th>
        </thead>
        <tbody>
          <tr v-for="(value, name) in data" :key="name">
            <td align="center" id="cols">{{ name }}</td>
            <td v-if="name === 'html'"><div v-html="value"></div></td>
            <td v-else-if="name === 'thumbnail_url'">
              <a :href="value">{{ value }}</a
              ><br /><img :src="value" />
            </td>
            <td v-else-if="isUrl(name)">
              <a :href="value">{{ value }}</a>
            </td>
            <td v-else>{{ value }}</td>
          </tr>
        </tbody>
      </table>
    </div>
    <!-- Vimeo -->
    <div v-if="provider === 'vimeo.com'" align="center">
      <table border="2" class="type07">
        <thead>
          <th align="center">Item</th>
          <th align="center">Contents</th>
        </thead>
        <tbody>
          <tr v-for="(value, name) in data" :key="name">
            <td align="center" id="cols">{{ name }}</td>
            <td v-if="name === 'html'"><div v-html="value"></div></td>
            <td v-else-if="name === 'thumbnail_url'">
              <a :href="value">{{ value }}</a
              ><br /><img :src="value" />
            </td>
            <td v-else-if="isUrl(name)">
              <a :href="value">{{ value }}</a>
            </td>
            <td v-else>{{ value }}</td>
          </tr>
        </tbody>
      </table>
    </div>
    <!-- Twitter -->
    <div v-if="provider === 'twitter.com'" align="center">
      <table border="2" class="type07">
        <thead>
          <th align="center">Item</th>
          <th align="center">Contents</th>
        </thead>
        <tbody>
          <tr v-for="(value, name) in data" :key="name">
            <td align="center" id="cols">{{ name }}</td>
            <td v-if="name === 'html'"><div v-html="value"></div></td>
            <td v-else-if="name === 'thumbnail_url'">
              <a :href="value">{{ value }}</a
              ><br /><img :src="value" />
            </td>
            <td v-else-if="isUrl(name)">
              <a :href="value">{{ value }}</a>
            </td>
            <td v-else>{{ value }}</td>
          </tr>
        </tbody>
      </table>
    </div>
    <!-- Instagram -->
    <div v-if="provider === 'www.instagram.com'" align="center">
      <table border="2" class="type07">
        <thead>
          <th align="center">Item</th>
          <th align="center">Contents</th>
        </thead>
        <tbody>
          <tr v-for="(value, name) in data" :key="name">
            <td align="center" id="cols">{{ name }}</td>
            <td v-if="name === 'html'"><div v-html="value"></div></td>
            <td v-else-if="name === 'thumbnail_url'">
              <a :href="value">{{ value }}</a
              ><br /><img :src="value" />
            </td>
            <td v-else-if="isUrl(name)">
              <a :href="value">{{ value }}</a>
            </td>
            <td v-else>{{ value }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </section>
</template>
<script>
import axios from "axios";
export default {
  data() {
    return {
      url: "",
      data: null,
      provider: null,
    };
  },
  methods: {
    // 스프링 서버 Http Get요청
    getOembed() {
      axios
        .get("http://localhost:8000/oembed/?url=" + this.url)
        .then((response) => {
          if (response.data.status == "err") {
            alert(response.data.msg);
          } else {
            this.data = response.data;
            this.provider = response.headers.provider;
            this.url = "";
          }
        })
        .catch(() => {
          alert("정보 획득이 불가능한 URL입니다.");
        });
    },
    // Json 데이터의 Key에 "url"이라는 단어 포함 여부
    isUrl(name) {
      return name.includes("url");
    },
  },
};
</script>
<style>
table.type07 {
  border-collapse: collapse;
  text-align: left;
  line-height: 1.5;
  border: 1px solid #ccc;
  margin: 20px 10px;
}
table.type07 thead {
  border-right: 1px solid #ccc;
  border-left: 1px solid #ccc;
  background: #e7708d;
  width: 100px;
}
table.type07 thead #chead {
  border-right: 1px solid #ccc;
  border-left: 1px solid #ccc;
  background: #e7708d;
  width: 10px;
}
table.type07 thead th {
  padding: 10px;
  font-weight: bold;
  vertical-align: top;
  color: #fff;
}
table.type07 tbody #cols {
  width: 10px;
  padding: 10px;
  font-weight: bold;
  vertical-align: top;
  border-bottom: 1px solid #ccc;
  background: #fcf1f4;
}
table.type07 tbody {
  width: 100px;
  padding: 10px;
  font-weight: bold;
  vertical-align: top;
  border-bottom: 1px solid #ccc;
  background: #fcf1f4;
}
table.type07 th {
  width: 50px;
  padding: 10px;
  vertical-align: top;
  border-bottom: 1px solid #ccc;
}
</style>
