DROP SEQUENCE IF EXISTS tblrecipeingredient_id_seq CASCADE;
DROP SEQUENCE IF EXISTS tblrecipeingredient_recipe_ingredient_id_seq CASCADE;
DROP SEQUENCE IF EXISTS hibernate_sequence CASCADE;
DROP SEQUENCE IF EXISTS tblcourse_course_id_seq CASCADE;
DROP SEQUENCE IF EXISTS tblcuisine_cuisine_id_seq CASCADE;
DROP SEQUENCE IF EXISTS tblfoodcategory_food_category_id_seq CASCADE;
DROP SEQUENCE IF EXISTS tblingredient_id_seq CASCADE;
DROP SEQUENCE IF EXISTS tblingredient_ingredient_id_seq CASCADE;
DROP SEQUENCE IF EXISTS tblingredientnutritionaninformation_rni_id_seq CASCADE;
DROP SEQUENCE IF EXISTS tblmeal_meal_id_seq CASCADE;
DROP SEQUENCE IF EXISTS tblmeasurement_measurement_id_seq CASCADE;
DROP SEQUENCE IF EXISTS tblnutritionalinformation_nutrition_information_id_seq CASCADE;
DROP SEQUENCE IF EXISTS tblrecipe_id_seq CASCADE;
DROP SEQUENCE IF EXISTS tblrecipe_recipe_id_seq CASCADE;

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

CREATE SEQUENCE public.hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.hibernate_sequence OWNER TO postgres;

SET default_tablespace = '';

SET default_with_oids = false;

CREATE TABLE public.tblcourse (
                                  course_id bigint NOT NULL,
                                  course character varying(255)
);


ALTER TABLE public.tblcourse OWNER TO postgres;

CREATE SEQUENCE public.tblcourse_course_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.tblcourse_course_id_seq OWNER TO postgres;

ALTER SEQUENCE public.tblcourse_course_id_seq OWNED BY public.tblcourse.course_id;

CREATE TABLE public.tblcuisine (
                                   cuisine_id bigint NOT NULL,
                                   cuisine character varying(255)
);


ALTER TABLE public.tblcuisine OWNER TO postgres;


CREATE SEQUENCE public.tblcuisine_cuisine_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.tblcuisine_cuisine_id_seq OWNER TO postgres;

ALTER SEQUENCE public.tblcuisine_cuisine_id_seq OWNED BY public.tblcuisine.cuisine_id;

CREATE TABLE public.tblfoodcategory (
                                        food_category_id bigint NOT NULL,
                                        food_category character varying(255)
);


ALTER TABLE public.tblfoodcategory OWNER TO postgres;

CREATE SEQUENCE public.tblfoodcategory_food_category_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.tblfoodcategory_food_category_id_seq OWNER TO postgres;

ALTER SEQUENCE public.tblfoodcategory_food_category_id_seq OWNED BY public.tblfoodcategory.food_category_id;

CREATE TABLE public.tblingredient (
                                      ingredient_id bigint NOT NULL,
                                      name character varying(255)
);


ALTER TABLE public.tblingredient OWNER TO postgres;

CREATE SEQUENCE public.tblingredient_ingredient_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.tblingredient_ingredient_id_seq OWNER TO postgres;

ALTER SEQUENCE public.tblingredient_ingredient_id_seq OWNED BY public.tblingredient.ingredient_id;

CREATE TABLE public.tblingredientnutritionaninformation (
                                                            rni_id bigint NOT NULL,
                                                            amount integer,
                                                            ingredient_id bigint,
                                                            ni_id bigint
);


ALTER TABLE public.tblingredientnutritionaninformation OWNER TO postgres;

CREATE SEQUENCE public.tblingredientnutritionaninformation_rni_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.tblingredientnutritionaninformation_rni_id_seq OWNER TO postgres;

ALTER SEQUENCE public.tblingredientnutritionaninformation_rni_id_seq OWNED BY public.tblingredientnutritionaninformation.rni_id;

CREATE TABLE public.tbllevel (
    level_id bigint NOT NULL
);

CREATE SEQUENCE public.tbllevel_level_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.tbllevel_level_id_seq OWNER TO postgres;

ALTER SEQUENCE public.tbllevel_level_id_seq OWNED BY public.tbllevel.level_id;


ALTER TABLE public.tbllevel OWNER TO postgres;

CREATE TABLE public.tblmeal (
                                meal_id bigint NOT NULL,
                                meal character varying(255)
);


ALTER TABLE public.tblmeal OWNER TO postgres;

CREATE SEQUENCE public.tblmeal_meal_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.tblmeal_meal_id_seq OWNER TO postgres;

ALTER SEQUENCE public.tblmeal_meal_id_seq OWNED BY public.tblmeal.meal_id;

CREATE TABLE public.tblmeasurement (
                                       measurement_id bigint NOT NULL,
                                       name character varying(255)
);


ALTER TABLE public.tblmeasurement OWNER TO postgres;

CREATE SEQUENCE public.tblmeasurement_measurement_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.tblmeasurement_measurement_id_seq OWNER TO postgres;

ALTER SEQUENCE public.tblmeasurement_measurement_id_seq OWNED BY public.tblmeasurement.measurement_id;

CREATE TABLE public.tblnutritionalinformation (
                                                  nutrition_information_id bigint NOT NULL,
                                                  name character varying(255)
);


ALTER TABLE public.tblnutritionalinformation OWNER TO postgres;

CREATE SEQUENCE public.tblnutritionalinformation_nutrition_information_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.tblnutritionalinformation_nutrition_information_id_seq OWNER TO postgres;

ALTER SEQUENCE public.tblnutritionalinformation_nutrition_information_id_seq OWNED BY public.tblnutritionalinformation.nutrition_information_id;

CREATE TABLE public.tblrecipe (
                                  recipe_id bigint NOT NULL,
                                  cooktime integer,
                                  cuisine_id integer,
                                  description character varying(255),
                                  imagepath character varying(255),
                                  instructions character varying(255),
                                  level_id integer,
                                  name character varying(255),
                                  rating integer
);


ALTER TABLE public.tblrecipe OWNER TO postgres;

CREATE SEQUENCE public.tblrecipe_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.tblrecipe_id_seq OWNER TO postgres;

CREATE SEQUENCE public.tblrecipe_recipe_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.tblrecipe_recipe_id_seq OWNER TO postgres;

ALTER SEQUENCE public.tblrecipe_recipe_id_seq OWNED BY public.tblrecipe.recipe_id;

CREATE TABLE public.tblrecipecourse (
                                        recipe_id bigint NOT NULL,
                                        course_id bigint NOT NULL
);


ALTER TABLE public.tblrecipecourse OWNER TO postgres;

CREATE TABLE public.tblrecipefoodcategory (
                                              recipe_id bigint NOT NULL,
                                              food_category_id bigint NOT NULL
);


ALTER TABLE public.tblrecipefoodcategory OWNER TO postgres;

CREATE TABLE public.tblrecipeingredient (
                                            recipe_ingredient_id bigint NOT NULL,
                                            amount integer,
                                            ingredient_id bigint NOT NULL,
                                            measurement_id bigint,
                                            recipe_id bigint NOT NULL
);


ALTER TABLE public.tblrecipeingredient OWNER TO postgres;

CREATE SEQUENCE public.tblrecipeingredient_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.tblrecipeingredient_id_seq OWNER TO postgres;

CREATE SEQUENCE public.tblrecipeingredient_recipe_ingredient_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.tblrecipeingredient_recipe_ingredient_id_seq OWNER TO postgres;

ALTER SEQUENCE public.tblrecipeingredient_recipe_ingredient_id_seq OWNED BY public.tblrecipeingredient.recipe_ingredient_id;


CREATE TABLE public.tblrecipemeals (
                                       recipe_id bigint NOT NULL,
                                       meal_id bigint NOT NULL
);

ALTER TABLE ONLY public.tblcourse
    ADD CONSTRAINT tblcourse_pkey PRIMARY KEY (course_id);

ALTER TABLE ONLY public.tblcuisine
    ADD CONSTRAINT tblcuisine_pkey PRIMARY KEY (cuisine_id);

ALTER TABLE ONLY public.tblfoodcategory
    ADD CONSTRAINT tblfoodcategory_pkey PRIMARY KEY (food_category_id);

ALTER TABLE ONLY public.tblingredient
    ADD CONSTRAINT tblingredient_pkey PRIMARY KEY (ingredient_id);

ALTER TABLE ONLY public.tblingredientnutritionaninformation
    ADD CONSTRAINT tblingredientnutritionaninformation_pkey PRIMARY KEY (rni_id);

ALTER TABLE ONLY public.tbllevel
    ADD CONSTRAINT tbllevel_pkey PRIMARY KEY (level_id);

ALTER TABLE ONLY public.tblmeal
    ADD CONSTRAINT tblmeal_pkey PRIMARY KEY (meal_id);

ALTER TABLE ONLY public.tblmeasurement
    ADD CONSTRAINT tblmeasurement_pkey PRIMARY KEY (measurement_id);

ALTER TABLE ONLY public.tblnutritionalinformation
    ADD CONSTRAINT tblnutritionalinformation_pkey PRIMARY KEY (nutrition_information_id);

ALTER TABLE ONLY public.tblrecipe
    ADD CONSTRAINT tblrecipe_pkey PRIMARY KEY (recipe_id);

ALTER TABLE ONLY public.tblrecipecourse
    ADD CONSTRAINT tblrecipecourse_pkey PRIMARY KEY (recipe_id, course_id);

ALTER TABLE ONLY public.tblrecipefoodcategory
    ADD CONSTRAINT tblrecipefoodcategory_pkey PRIMARY KEY (recipe_id, food_category_id);

ALTER TABLE ONLY public.tblrecipeingredient
    ADD CONSTRAINT tblrecipeingredient_pkey PRIMARY KEY (recipe_ingredient_id);

ALTER TABLE ONLY public.tblrecipemeals
    ADD CONSTRAINT tblrecipemeals_pkey PRIMARY KEY (recipe_id, meal_id);

ALTER TABLE ONLY public.tblrecipemeals
    ADD CONSTRAINT fk3dhyy6h711whxlp5iwokkswmv FOREIGN KEY (meal_id) REFERENCES public.tblmeal(meal_id);

ALTER TABLE ONLY public.tblrecipecourse
    ADD CONSTRAINT fk3dv61nxdrwpp6be6tjc5p37uf FOREIGN KEY (recipe_id) REFERENCES public.tblrecipe(recipe_id);


ALTER TABLE ONLY public.tblrecipecourse
    ADD CONSTRAINT fk4ay8lsegi073o0o2irqnfg6q5 FOREIGN KEY (course_id) REFERENCES public.tblcourse(course_id);

ALTER TABLE ONLY public.tblingredientnutritionaninformation
    ADD CONSTRAINT fk7puwcsh398il6es57menv3ay1 FOREIGN KEY (ingredient_id) REFERENCES public.tblingredient(ingredient_id);


ALTER TABLE ONLY public.tblingredientnutritionaninformation
    ADD CONSTRAINT fk9xmr3i1umipvquuynb0v49t63 FOREIGN KEY (ni_id) REFERENCES public.tblnutritionalinformation(nutrition_information_id);

ALTER TABLE ONLY public.tblrecipeingredient
    ADD CONSTRAINT fkgd4gu80ig9dok0pqj8t6dvrv5 FOREIGN KEY (measurement_id) REFERENCES public.tblmeasurement(measurement_id);

ALTER TABLE ONLY public.tblrecipe
    ADD CONSTRAINT fkkg8srao99674ttvtehs87uonh FOREIGN KEY (level_id) REFERENCES public.tbllevel(level_id);

ALTER TABLE ONLY public.tblrecipe
    ADD CONSTRAINT fkl77bo75y9w0baiqgsm18pakax FOREIGN KEY (cuisine_id) REFERENCES public.tblcuisine(cuisine_id);

ALTER TABLE ONLY public.tblrecipemeals
    ADD CONSTRAINT fkpd5uwaw519kihlvh0xqla7k9r FOREIGN KEY (recipe_id) REFERENCES public.tblrecipe(recipe_id);

ALTER TABLE ONLY public.tblrecipefoodcategory
    ADD CONSTRAINT fkqyebjeynds0eqnf9yk8tufb7m FOREIGN KEY (food_category_id) REFERENCES public.tblfoodcategory(food_category_id);

ALTER TABLE ONLY public.tblrecipeingredient
    ADD CONSTRAINT fksh8od87j5i7unbbwqb0dqbfpc FOREIGN KEY (ingredient_id) REFERENCES public.tblingredient(ingredient_id);

ALTER TABLE ONLY public.tblrecipeingredient
    ADD CONSTRAINT fksnsnvm186r7xhob8t6p39v53k FOREIGN KEY (recipe_id) REFERENCES public.tblrecipe(recipe_id);

ALTER TABLE ONLY public.tblrecipefoodcategory
    ADD CONSTRAINT fksu4nfv25lilhw6eg0j3gtcnsn FOREIGN KEY (recipe_id) REFERENCES public.tblrecipe(recipe_id);


ALTER TABLE public.tblrecipemeals OWNER TO postgres;

ALTER TABLE ONLY public.tbllevel ALTER COLUMN level_id SET DEFAULT nextval('public.tbllevel_level_id_seq'::regclass);
ALTER TABLE ONLY public.tblcourse ALTER COLUMN course_id SET DEFAULT nextval('public.tblcourse_course_id_seq'::regclass);
ALTER TABLE ONLY public.tblcuisine ALTER COLUMN cuisine_id SET DEFAULT nextval('public.tblcuisine_cuisine_id_seq'::regclass);
ALTER TABLE ONLY public.tblfoodcategory ALTER COLUMN food_category_id SET DEFAULT nextval('public.tblfoodcategory_food_category_id_seq'::regclass);
ALTER TABLE ONLY public.tblingredient ALTER COLUMN ingredient_id SET DEFAULT nextval('public.tblingredient_ingredient_id_seq'::regclass);
ALTER TABLE ONLY public.tblingredientnutritionaninformation ALTER COLUMN rni_id SET DEFAULT nextval('public.tblingredientnutritionaninformation_rni_id_seq'::regclass);
ALTER TABLE ONLY public.tblmeal ALTER COLUMN meal_id SET DEFAULT nextval('public.tblmeal_meal_id_seq'::regclass);
ALTER TABLE ONLY public.tblmeasurement ALTER COLUMN measurement_id SET DEFAULT nextval('public.tblmeasurement_measurement_id_seq'::regclass);
ALTER TABLE ONLY public.tblnutritionalinformation ALTER COLUMN nutrition_information_id SET DEFAULT nextval('public.tblnutritionalinformation_nutrition_information_id_seq'::regclass);
ALTER TABLE ONLY public.tblrecipe ALTER COLUMN recipe_id SET DEFAULT nextval('public.tblrecipe_recipe_id_seq'::regclass);
ALTER TABLE ONLY public.tblrecipeingredient ALTER COLUMN recipe_ingredient_id SET DEFAULT nextval('public.tblrecipeingredient_recipe_ingredient_id_seq'::regclass);
