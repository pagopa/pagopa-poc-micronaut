const axios = require("axios");
const fs = require('fs');

const reporting_enrollment_host = process.env.REPORTING_ENROLLMENT_HOST;

function get(url) {
    return axios.get(reporting_enrollment_host + url)
         .then(res => {
             return res;
         })
         .catch(error => {
             return error.response;
         });
}

function post(url, headers, body) {
    return axios.post(reporting_enrollment_host + url, body, {headers})
        .then(res => {
            return res;
        })
        .catch(error => {
            return error.response;
        });
}

function put(url, body) {
    return axios.put(reporting_enrollment_host + url, body)
        .then(res => {
            return res;
        })
        .catch(error => {
            return error.response;
        });
}


function del(url) {
    return axios.delete(reporting_enrollment_host + url)
        .then(res => {
            return res;
        })
        .catch(error => {
            return error.response;
        });
}

function randomOrg() {
    return "Org_" + Math.floor(Math.random() * 100);
}


module.exports = {get, post, put, del, randomOrg}
