DROP SEQUENCE IF EXISTS tblrecipeingredient_id_seq;
DROP SEQUENCE IF EXISTS tblrecipeingredient_recipe_ingredient_id_seq ;
DROP SEQUENCE IF EXISTS hibernate_sequence ;
DROP SEQUENCE IF EXISTS tblcourse_course_id_seq ;
DROP SEQUENCE IF EXISTS tblcuisine_cuisine_id_seq ;
DROP SEQUENCE IF EXISTS tblfoodcategory_food_category_id_seq ;
DROP SEQUENCE IF EXISTS tblingredient_id_seq ;
DROP SEQUENCE IF EXISTS tblingredient_ingredient_id_seq ;
DROP SEQUENCE IF EXISTS tblingredientnutritionaninformation_rni_id_seq ;
DROP SEQUENCE IF EXISTS tblmeal_meal_id_seq ;
DROP SEQUENCE IF EXISTS tblmeasurement_measurement_id_seq ;
DROP SEQUENCE IF EXISTS tblnutritionalinformation_nutrition_information_id_seq ;
DROP SEQUENCE IF EXISTS tblrecipe_id_seq ;
DROP SEQUENCE IF EXISTS tblrecipe_recipe_id_seq ;

DROP TABLE IF EXISTS tblcourse CASCADE;
DROP TABLE IF EXISTS tblcuisine CASCADE;
DROP TABLE IF EXISTS tblfoodcategory CASCADE;
DROP TABLE IF EXISTS tblingredient CASCADE;
DROP TABLE IF EXISTS tblingredientnutritionaninformation CASCADE;
DROP TABLE IF EXISTS tblmeasurement CASCADE;
DROP TABLE IF EXISTS tbllevel CASCADE;
DROP TABLE IF EXISTS tblmeal CASCADE;
DROP TABLE IF EXISTS tblnutritionalinformation CASCADE;
DROP TABLE IF EXISTS tblrecipe CASCADE;
DROP TABLE IF EXISTS tblrecipecourse CASCADE;
DROP TABLE IF EXISTS tblrecipefoodcategory CASCADE;
DROP TABLE IF EXISTS tblrecipeingredient CASCADE;
DROP TABLE IF EXISTS tblrecipemeals CASCADE;

-- CREATE SEQUENCE hibernate_sequence
--     START WITH 1
--     INCREMENT BY 1
--     NO MINVALUE
--     NO MAXVALUE
--     CACHE 1;
CREATE TABLE tblcourse (
                                  course_id bigserial NOT NULL,
                                  course varchar(255)
);

-- CREATE SEQUENCE tblcourse_course_id_seq
--     START WITH 1
--     INCREMENT BY 1
--     NO MINVALUE
--     NO MAXVALUE
--     CACHE 1;

CREATE TABLE tblcuisine (
                                   cuisine_id bigserial NOT NULL,
                                   cuisine varchar(255)
);

-- CREATE SEQUENCE tblcuisine_cuisine_id_seq
--     START WITH 1
--     INCREMENT BY 1
--     NO MINVALUE
--     NO MAXVALUE
--     CACHE 1;

CREATE TABLE tblfoodcategory (
                                        food_category_id bigserial NOT NULL,
                                        food_category varchar(255)
);

-- CREATE SEQUENCE tblfoodcategory_food_category_id_seq
--     START WITH 1
--     INCREMENT BY 1
--     NO MINVALUE
--     NO MAXVALUE
--     CACHE 1;

CREATE TABLE tblingredient (
                                      ingredient_id bigserial NOT NULL,
                                      name varchar(255)
);

-- CREATE SEQUENCE tblingredient_ingredient_id_seq
--     START WITH 1
--     INCREMENT BY 1
--     NO MINVALUE
--     NO MAXVALUE
--     CACHE 1;

CREATE TABLE tblingredientnutritionaninformation (
                                                            rni_id bigserial NOT NULL,
                                                            amount integer,
                                                            ingredient_id bigint,
                                                            ni_id bigint
);

-- CREATE SEQUENCE tblingredientnutritionaninformation_rni_id_seq
--     START WITH 1
--     INCREMENT BY 1
--     NO MINVALUE
--     NO MAXVALUE
--     CACHE 1;

CREATE TABLE tbllevel (
    level_id bigserial NOT NULL
);

-- CREATE SEQUENCE tbllevel_level_id_seq
--     START WITH 1
--     INCREMENT BY 1
--     NO MINVALUE
--     NO MAXVALUE
--     CACHE 1;

CREATE TABLE tblmeal (
                                meal_id bigserial NOT NULL,
                                meal varchar(255)
);

-- CREATE SEQUENCE tblmeal_meal_id_seq
--     START WITH 1
--     INCREMENT BY 1
--     NO MINVALUE
--     NO MAXVALUE
--     CACHE 1;

CREATE TABLE tblmeasurement (
                                       measurement_id bigserial NOT NULL,
                                       name varchar(255)
);

-- CREATE SEQUENCE tblmeasurement_measurement_id_seq
--     START WITH 1
--     INCREMENT BY 1
--     NO MINVALUE
--     NO MAXVALUE
--     CACHE 1;

CREATE TABLE tblnutritionalinformation (
                                                  nutrition_information_id bigserial NOT NULL,
                                                  name varchar(255)
);

-- CREATE SEQUENCE tblnutritionalinformation_nutrition_information_id_seq
--     START WITH 1
--     INCREMENT BY 1
--     NO MINVALUE
--     NO MAXVALUE
--     CACHE 1;

CREATE TABLE tblrecipe (
                                  recipe_id bigserial NOT NULL,
                                  cooktime integer,
                                  cuisine_id integer,
                                  description varchar(255),
                                  imagepath varchar(255),
                                  instructions varchar(255),
                                  level_id integer,
                                  name varchar(255),
                                  rating integer
);

-- CREATE SEQUENCE tblrecipe_id_seq
--     START WITH 1
--     INCREMENT BY 1
--     NO MINVALUE
--     NO MAXVALUE
--     CACHE 1;
--
-- CREATE SEQUENCE tblrecipe_recipe_id_seq
--     START WITH 1
--     INCREMENT BY 1
--     NO MINVALUE
--     NO MAXVALUE
--     CACHE 1;

CREATE TABLE tblrecipecourse (
                                        recipe_id bigint NOT NULL,
                                        course_id bigint NOT NULL
);

CREATE TABLE tblrecipefoodcategory (
                                              recipe_id bigint NOT NULL,
                                              food_category_id bigint NOT NULL
);

CREATE TABLE tblrecipeingredient (
                                            recipe_ingredient_id bigint NOT NULL,
                                            amount integer,
                                            ingredient_id bigint NOT NULL,
                                            measurement_id bigint,
                                            recipe_id bigint NOT NULL
);

-- CREATE SEQUENCE tblrecipeingredient_id_seq
--     START WITH 1
--     INCREMENT BY 1
--     NO MINVALUE
--     NO MAXVALUE
--     CACHE 1;
--
-- CREATE SEQUENCE tblrecipeingredient_recipe_ingredient_id_seq
--     START WITH 1
--     INCREMENT BY 1
--     NO MINVALUE
--     NO MAXVALUE
--     CACHE 1;

CREATE TABLE tblrecipemeals (
                                       recipe_id bigint NOT NULL,
                                       meal_id bigint NOT NULL
);

-- ALTER TABLE tblcourse
--     ADD CONSTRAINT tblcourse_pkey PRIMARY KEY (course_id);
--
-- ALTER TABLE tblcuisine
--     ADD CONSTRAINT tblcuisine_pkey PRIMARY KEY (cuisine_id);
--
-- ALTER TABLE tblfoodcategory
--     ADD CONSTRAINT tblfoodcategory_pkey PRIMARY KEY (food_category_id);
--
-- ALTER TABLE tblingredient
--     ADD CONSTRAINT tblingredient_pkey PRIMARY KEY (ingredient_id);
--
-- ALTER TABLE tblingredientnutritionaninformation
--     ADD CONSTRAINT tblingredientnutritionaninformation_pkey PRIMARY KEY (rni_id);
--
-- ALTER TABLE tbllevel
--     ADD CONSTRAINT tbllevel_pkey PRIMARY KEY (level_id);
--
-- ALTER TABLE tblmeal
--     ADD CONSTRAINT tblmeal_pkey PRIMARY KEY (meal_id);
--
-- ALTER TABLE tblmeasurement
--     ADD CONSTRAINT tblmeasurement_pkey PRIMARY KEY (measurement_id);
--
-- ALTER TABLE tblnutritionalinformation
--     ADD CONSTRAINT tblnutritionalinformation_pkey PRIMARY KEY (nutrition_information_id);
--
-- ALTER TABLE tblrecipe
--     ADD CONSTRAINT tblrecipe_pkey PRIMARY KEY (recipe_id);
--
-- ALTER TABLE tblrecipecourse
--     ADD CONSTRAINT tblrecipecourse_pkey PRIMARY KEY (recipe_id, course_id);
--
-- ALTER TABLE tblrecipefoodcategory
--     ADD CONSTRAINT tblrecipefoodcategory_pkey PRIMARY KEY (recipe_id, food_category_id);
--
-- ALTER TABLE tblrecipeingredient
--     ADD CONSTRAINT tblrecipeingredient_pkey PRIMARY KEY (recipe_ingredient_id);
--
-- ALTER TABLE tblrecipemeals
--     ADD CONSTRAINT tblrecipemeals_pkey PRIMARY KEY (recipe_id, meal_id);

ALTER TABLE tblrecipemeals
    ADD CONSTRAINT fk3dhyy6h711whxlp5iwokkswmv FOREIGN KEY (meal_id) REFERENCES tblmeal(meal_id);

ALTER TABLE tblrecipecourse
    ADD CONSTRAINT fk3dv61nxdrwpp6be6tjc5p37uf FOREIGN KEY (recipe_id) REFERENCES tblrecipe(recipe_id);


ALTER TABLE tblrecipecourse
    ADD CONSTRAINT fk4ay8lsegi073o0o2irqnfg6q5 FOREIGN KEY (course_id) REFERENCES tblcourse(course_id);

ALTER TABLE tblingredientnutritionaninformation
    ADD CONSTRAINT fk7puwcsh398il6es57menv3ay1 FOREIGN KEY (ingredient_id) REFERENCES tblingredient(ingredient_id);


ALTER TABLE tblingredientnutritionaninformation
    ADD CONSTRAINT fk9xmr3i1umipvquuynb0v49t63 FOREIGN KEY (ni_id) REFERENCES tblnutritionalinformation(nutrition_information_id);

ALTER TABLE tblrecipeingredient
    ADD CONSTRAINT fkgd4gu80ig9dok0pqj8t6dvrv5 FOREIGN KEY (measurement_id) REFERENCES tblmeasurement(measurement_id);

ALTER TABLE tblrecipe
    ADD CONSTRAINT fkkg8srao99674ttvtehs87uonh FOREIGN KEY (level_id) REFERENCES tbllevel(level_id);

ALTER TABLE tblrecipe
    ADD CONSTRAINT fkl77bo75y9w0baiqgsm18pakax FOREIGN KEY (cuisine_id) REFERENCES tblcuisine(cuisine_id);

ALTER TABLE tblrecipemeals
    ADD CONSTRAINT fkpd5uwaw519kihlvh0xqla7k9r FOREIGN KEY (recipe_id) REFERENCES tblrecipe(recipe_id);

ALTER TABLE tblrecipefoodcategory
    ADD CONSTRAINT fkqyebjeynds0eqnf9yk8tufb7m FOREIGN KEY (food_category_id) REFERENCES tblfoodcategory(food_category_id);

ALTER TABLE tblrecipeingredient
    ADD CONSTRAINT fksh8od87j5i7unbbwqb0dqbfpc FOREIGN KEY (ingredient_id) REFERENCES tblingredient(ingredient_id);

ALTER TABLE tblrecipeingredient
    ADD CONSTRAINT fksnsnvm186r7xhob8t6p39v53k FOREIGN KEY (recipe_id) REFERENCES tblrecipe(recipe_id);

ALTER TABLE tblrecipefoodcategory
    ADD CONSTRAINT fksu4nfv25lilhw6eg0j3gtcnsn FOREIGN KEY (recipe_id) REFERENCES tblrecipe(recipe_id);

-- ALTER TABLE tbllevel ALTER COLUMN level_id SET DEFAULT nextval('tbllevel_level_id_seq'::regclass);
-- ALTER TABLE tblcourse ALTER COLUMN course_id SET DEFAULT nextval('tblcourse_course_id_seq'::regclass);
-- ALTER TABLE tblcuisine ALTER COLUMN cuisine_id SET DEFAULT nextval('tblcuisine_cuisine_id_seq'::regclass);
-- ALTER TABLE tblfoodcategory ALTER COLUMN food_category_id SET DEFAULT nextval('tblfoodcategory_food_category_id_seq'::regclass);
-- ALTER TABLE tblingredient ALTER COLUMN ingredient_id SET DEFAULT nextval('tblingredient_ingredient_id_seq'::regclass);
-- ALTER TABLE tblingredientnutritionaninformation ALTER COLUMN rni_id SET DEFAULT nextval('tblingredientnutritionaninformation_rni_id_seq'::regclass);
-- ALTER TABLE tblmeal ALTER COLUMN meal_id SET DEFAULT nextval('tblmeal_meal_id_seq'::regclass);
-- ALTER TABLE tblmeasurement ALTER COLUMN measurement_id SET DEFAULT nextval('tblmeasurement_measurement_id_seq'::regclass);
-- ALTER TABLE tblnutritionalinformation ALTER COLUMN nutrition_information_id SET DEFAULT nextval('tblnutritionalinformation_nutrition_information_id_seq'::regclass);
-- ALTER TABLE tblrecipe ALTER COLUMN recipe_id SET DEFAULT nextval('tblrecipe_recipe_id_seq'::regclass);
-- ALTER TABLE tblrecipeingredient ALTER COLUMN recipe_ingredient_id SET DEFAULT nextval('tblrecipeingredient_recipe_ingredient_id_seq'::regclass);
