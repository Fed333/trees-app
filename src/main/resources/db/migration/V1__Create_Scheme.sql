--
-- Name: pgcrypto; Type: EXTENSION; Schema: -; Owner:
--

CREATE EXTENSION IF NOT EXISTS pgcrypto WITH SCHEMA public;
-- public.tree_task_type definition

-- Drop table

-- DROP TABLE public.tree_task_type;

CREATE TABLE public.tree_task_type (
	id int8 NOT NULL,
	execution_time int4 NULL,
    name varchar(40) NULL,
	CONSTRAINT tree_task_type_pkey PRIMARY KEY (id)
);

ALTER TABLE public.tree_task_type ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.tree_task_type_id_seq
START WITH 1
          INCREMENT BY 1
          NO MINVALUE
          NO MAXVALUE
          CACHE 1
          );

-- public.tree_type definition

-- Drop table

-- DROP TABLE public.tree_type;

CREATE TABLE public.tree_type (
	id int8 NOT NULL,
	description varchar NULL,
	name varchar(20) NULL,
	CONSTRAINT tree_type_pkey PRIMARY KEY (id)
);

ALTER TABLE public.tree_type ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.tree_type_id_seq
START WITH 1
          INCREMENT BY 1
          NO MINVALUE
          NO MAXVALUE
          CACHE 1
          );

-- public.users definition

-- Drop table

-- DROP TABLE public.users;

CREATE TABLE public.users (
	id int8 NOT NULL,
	email varchar(64) NULL,
	full_name varchar(64) NULL,
	password varchar(255) NULL,
	CONSTRAINT users_pkey PRIMARY KEY (id)
);

ALTER TABLE public.users ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.users_id_seq
START WITH 1
          INCREMENT BY 1
          NO MINVALUE
          NO MAXVALUE
          CACHE 1
          );

-- public.tree definition

-- Drop table

-- DROP TABLE public.tree;

CREATE TABLE public.tree (
	id int8 NOT NULL,
	birth_date timestamp NOT NULL,
	photo_url varchar NULL,
	radius int4 NOT NULL,
	register_number varchar(255) NOT NULL,
	state varchar(10) NULL,
	x float8 NOT NULL,
	y float8 NOT NULL,
	type_id int8 NULL,
	CONSTRAINT tree_pkey PRIMARY KEY (id),
	CONSTRAINT tree_tree_type_fk FOREIGN KEY (type_id) REFERENCES public.tree_type(id)
);


ALTER TABLE public.tree ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.tree_id_seq
START WITH 1
          INCREMENT BY 1
          NO MINVALUE
          NO MAXVALUE
          CACHE 1
          );

-- public.user_role definition

-- Drop table

-- DROP TABLE public.user_role;

CREATE TABLE public.user_role (
	user_id int8 NOT NULL,
	roles varchar(255) NULL,
	CONSTRAINT user_role_fk FOREIGN KEY (user_id) REFERENCES public.users(id)
);


-- public.assigned_tree_task definition

-- Drop table

-- DROP TABLE public.assigned_tree_task;

CREATE TABLE public.assigned_tree_task (
	id int8 NOT NULL,
	end_date timestamp NULL,
	status varchar(255) NULL,
	task_type_id int8 NULL,
	tree_id int8 NULL,
	CONSTRAINT assigned_tree_task_pkey PRIMARY KEY (id),
	CONSTRAINT assigned_tree_task_task_type_fk FOREIGN KEY (task_type_id) REFERENCES public.tree_task_type(id),
	CONSTRAINT assigned_tree_task_tree_fk FOREIGN KEY (tree_id) REFERENCES public.tree(id)
);

ALTER TABLE public.assigned_tree_task ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.assigned_tree_task_id_seq
START WITH 1
          INCREMENT BY 1
          NO MINVALUE
          NO MAXVALUE
          CACHE 1
          );

-- public.tree_tasks definition

-- Drop table

-- DROP TABLE public.tree_tasks;

CREATE TABLE public.tree_tasks (
	tree_id int8 NOT NULL,
	tasks_id int8 NOT NULL,
	CONSTRAINT tree_tasks_tasks_id_unique UNIQUE (tasks_id),
	CONSTRAINT tree_id_tree_fk FOREIGN KEY (tree_id) REFERENCES public.tree(id),
	CONSTRAINT tasks_id_assigned_tree_task_fk FOREIGN KEY (tasks_id) REFERENCES public.assigned_tree_task(id)
);