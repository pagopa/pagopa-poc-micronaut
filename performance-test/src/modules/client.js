import http from 'k6/http';

const key = `${__ENV.ORG_ENROLLMENT_SUBSCRIPTION_KEY}`


export function postOrganization(rootUrl, organizationFiscalCode) {
	const url = `${rootUrl}/${organizationFiscalCode}?frameworkType=micronaut`
	const params = {
        headers: {
            'Content-Type': 'application/json',
			'Ocp-Apim-Subscription-Key': key
        },
    };
	return http.post(url, {}, params);
}

export function deleteOrganization(rootUrl, organizationFiscalCode) {
	const url = `${rootUrl}/${organizationFiscalCode}?frameworkType=micronaut`
	const params = {
        headers: {
			'Content-Type': 'application/json',
			'Ocp-Apim-Subscription-Key': key
        },
    };
	return http.del(url, {}, params);
}

export function getOrganization(rootUrl, organizationFiscalCode) {
	const params = {
        headers: {
			'Ocp-Apim-Subscription-Key': key
        },
    };
	const url = `${rootUrl}/${organizationFiscalCode}?frameworkType=micronaut`
    return http.get(url, params);
}

export function getOrganizations(rootUrl) {
	const url = `${rootUrl}?frameworkType=micronaut`
	const params = {
        headers: {
			'Ocp-Apim-Subscription-Key': key
        },
    };
    return http.get(rootUrl, params);
}
