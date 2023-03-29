import { check } from 'k6';
import { SharedArray } from 'k6/data';

import { getMockFiscalCode } from './modules/helpers.js';

import { postOrganization, deleteOrganization } from "./modules/client.js";

export let options = JSON.parse(open(__ENV.TEST_TYPE));

const varsArray = new SharedArray('vars', function () {
	return JSON.parse(open(`./${__ENV.VARS}`)).environment;
});
// workaround to use shared array (only array should be used)
const vars = varsArray[0];
const rootUrl = `${vars.host}/${vars.basePath}`;

function postcondition(organizationFiscalCode) {
	// Delete the newly created organization
	let tag = {
		gpdMethod: "DeleteOrganization",
	};

	let r = deleteOrganization(rootUrl, organizationFiscalCode);

	check(r, {
		"DeleteOrganization status is 200": (_r) => r.status === 200,
	}, tag);
}

export default function() {
    let tag = {
        gpsMethod: "CREATE organization",
    };

    let organizationFiscalCode = getMockFiscalCode(25);

	let r = postOrganization(rootUrl, organizationFiscalCode);

	check(r, {
		'CreateOrganization status is 201': (_r) => r.status === 201,
	}, tag);

	if (r.status === 201) {
		postcondition(organizationFiscalCode);
	}
}
