INSERT INTO classifiers(title, description)
VALUES('RISK_TYPE', 'risk type');

INSERT INTO classifier_values(
	classifier_id,
    ic,
    description)
SELECT
	cl.id,
    'TRAVEL_MEDICAL',
    'medical risk'
 FROM classifiers as cl
 WHERE cl.title = 'RISK_TYPE';


INSERT INTO classifier_values(
	classifier_id,
    ic,
    description)
SELECT
	cl.id,
    'TRAVEL_CANCELLATION',
    'travel cancellation risk'
 FROM classifiers as cl
 WHERE cl.title = 'RISK_TYPE';


INSERT INTO classifier_values(
	classifier_id,
    ic,
    description)
SELECT
	cl.id,
    'TRAVEL_LOSS_BAGGAGE',
    'risk of lost baggage'
 FROM classifiers as cl
 WHERE cl.title = 'RISK_TYPE';


INSERT INTO classifier_values(
	classifier_id,
    ic,
    description)
SELECT
	cl.id,
    'TRAVEL_THIRD_PARTY_LIABILITY',
    'third party liability risk'
 FROM classifiers as cl
 WHERE cl.title = 'RISK_TYPE';


INSERT INTO classifier_values(
	classifier_id,
    ic,
    description)
SELECT
	cl.id,
    'TRAVEL_EVACUATION',
    'evacuation risk'
 FROM classifiers as cl
 WHERE cl.title = 'RISK_TYPE';


INSERT INTO classifier_values(
	classifier_id,
    ic,
    description)
SELECT
	cl.id,
    'TRAVEL_SPORT_ACTIVITIES',
    'risk of sporting events'
 FROM classifiers as cl
 WHERE cl.title = 'RISK_TYPE';


INSERT INTO classifiers(title, description)
VALUES('COUNTRY', 'Country classifier');

INSERT INTO classifier_values(
	classifier_id,
    ic,
    description)
SELECT
	cl.id,
    'LATVIA',
    'Country Latvia'
 FROM classifiers as cl
 WHERE cl.title = 'COUNTRY';

INSERT INTO classifier_values(
	classifier_id,
    ic,
    description)
SELECT
	cl.id,
    'SPAIN',
    'Country Spain'
 FROM classifiers as cl
 WHERE cl.title = 'COUNTRY';

INSERT INTO classifier_values(
	classifier_id,
    ic,
    description)
SELECT
	cl.id,
    'JAPAN',
    'Country Japan'
 FROM classifiers as cl
 WHERE cl.title = 'COUNTRY';


INSERT INTO travel_medical_country_default_day_rate(country_ic, default_day_rate)
VALUES('LATVIA', 1.00);

INSERT INTO travel_medical_country_default_day_rate(country_ic, default_day_rate)
VALUES('SPAIN', 2.50);

INSERT INTO travel_medical_country_default_day_rate(country_ic, default_day_rate)
VALUES('JAPAN', 3.50);



INSERT INTO travel_medical_age_coefficient(age_from, age_to, coefficient)
VALUES(0, 5, 1.1);

INSERT INTO travel_medical_age_coefficient(age_from, age_to, coefficient)
VALUES(6, 10, 0.7);

INSERT INTO travel_medical_age_coefficient(age_from, age_to, coefficient)
VALUES(11, 17, 1.0);

INSERT INTO travel_medical_age_coefficient(age_from, age_to, coefficient)
VALUES(18, 40, 1.1);

INSERT INTO travel_medical_age_coefficient(age_from, age_to, coefficient)
VALUES(41, 65, 1.2);

INSERT INTO travel_medical_age_coefficient(age_from, age_to, coefficient)
VALUES(66, 150, 1.5);


INSERT INTO classifiers(title, description)
VALUES('MEDICAL_RISK_LIMIT_LEVEL', 'medical risk limit level');

INSERT INTO classifier_values(
	classifier_id,
    ic,
    description)
SELECT
	cl.id,
    'LEVEL_10000',
    'medical risk 10000 euro limit level'
 FROM classifiers as cl
 WHERE cl.title = 'MEDICAL_RISK_LIMIT_LEVEL';

INSERT INTO classifier_values(
	classifier_id,
    ic,
    description)
SELECT
	cl.id,
    'LEVEL_15000',
    'medical risk 15000 euro limit level'
 FROM classifiers as cl
 WHERE cl.title = 'MEDICAL_RISK_LIMIT_LEVEL';

INSERT INTO classifier_values(
	classifier_id,
    ic,
    description)
SELECT
	cl.id,
    'LEVEL_20000',
    'medical risk 20000 euro limit level'
 FROM classifiers as cl
 WHERE cl.title = 'MEDICAL_RISK_LIMIT_LEVEL';


INSERT INTO classifier_values(
	classifier_id,
    ic,
    description)
SELECT
	cl.id,
    'LEVEL_50000',
    'medical risk 50000 euro limit level'
 FROM classifiers as cl
 WHERE cl.title = 'MEDICAL_RISK_LIMIT_LEVEL';


INSERT INTO travel_medical_risk_limit_level(medical_risk_limit_level_ic, coefficient)
VALUES('LEVEL_10000', 1.0);

INSERT INTO travel_medical_risk_limit_level(medical_risk_limit_level_ic, coefficient)
VALUES('LEVEL_15000', 1.2);

INSERT INTO travel_medical_risk_limit_level(medical_risk_limit_level_ic, coefficient)
VALUES('LEVEL_20000', 1.5);

INSERT INTO travel_medical_risk_limit_level(medical_risk_limit_level_ic, coefficient)
VALUES('LEVEL_50000', 2.0);


INSERT INTO travel_cancellation_travel_cost_coefficient(travel_cost_from, travel_cost_to, coefficient)
VALUES(0, 4999.99, 10.0);

INSERT INTO travel_cancellation_travel_cost_coefficient(travel_cost_from, travel_cost_to, coefficient)
VALUES(5000, 9999.99, 30.0);

INSERT INTO travel_cancellation_travel_cost_coefficient(travel_cost_from, travel_cost_to, coefficient)
VALUES(10000, 19999.99, 100.0);

INSERT INTO travel_cancellation_travel_cost_coefficient(travel_cost_from, travel_cost_to, coefficient)
VALUES(20000, 100000, 500.0);


INSERT INTO travel_cancellation_age_coefficient(age_from, age_to, coefficient)
VALUES(0, 9, 5.0);

INSERT INTO travel_cancellation_age_coefficient(age_from, age_to, coefficient)
VALUES(10, 17, 10.0);

INSERT INTO travel_cancellation_age_coefficient(age_from, age_to, coefficient)
VALUES(18, 39, 20.0);

INSERT INTO travel_cancellation_age_coefficient(age_from, age_to, coefficient)
VALUES(40, 64, 30.0);

INSERT INTO travel_cancellation_age_coefficient(age_from, age_to, coefficient)
VALUES(65, 115, 50.0);


INSERT INTO travel_cancellation_country_safety_rating_coefficient(country_ic, coefficient)
VALUES('LATVIA', 5.0);

INSERT INTO travel_cancellation_country_safety_rating_coefficient(country_ic, coefficient)
VALUES('SPAIN', 8.0);

INSERT INTO travel_cancellation_country_safety_rating_coefficient(country_ic, coefficient)
VALUES('JAPAN', 9.0);











