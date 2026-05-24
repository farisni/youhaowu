--
-- PostgreSQL database dump
--

\restrict bS8Oh0sIYbe4OcPTgIuDSkIpePD6dapQUzP11sO4f48WufhplCho3L4fBnr7FfU

-- Dumped from database version 17.10
-- Dumped by pg_dump version 18.4

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: config_info; Type: TABLE; Schema: public; Owner: faris
--

CREATE TABLE public.config_info (
    id bigint NOT NULL,
    data_id character varying(255) NOT NULL,
    group_id character varying(255),
    content text NOT NULL,
    md5 character varying(32),
    gmt_create timestamp(6) without time zone NOT NULL,
    gmt_modified timestamp(6) without time zone NOT NULL,
    src_user text,
    src_ip character varying(20),
    app_name character varying(128),
    tenant_id character varying(128),
    c_desc character varying(256),
    c_use character varying(64),
    effect character varying(64),
    type character varying(64),
    c_schema text,
    encrypted_data_key character varying(1024) NOT NULL
);


ALTER TABLE public.config_info OWNER TO faris;

--
-- Name: TABLE config_info; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON TABLE public.config_info IS 'config_info';


--
-- Name: COLUMN config_info.id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.config_info.id IS 'id';


--
-- Name: COLUMN config_info.data_id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.config_info.data_id IS 'data_id';


--
-- Name: COLUMN config_info.content; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.config_info.content IS 'content';


--
-- Name: COLUMN config_info.md5; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.config_info.md5 IS 'md5';


--
-- Name: COLUMN config_info.gmt_create; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.config_info.gmt_create IS '创建时间';


--
-- Name: COLUMN config_info.gmt_modified; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.config_info.gmt_modified IS '修改时间';


--
-- Name: COLUMN config_info.src_user; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.config_info.src_user IS 'source user';


--
-- Name: COLUMN config_info.src_ip; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.config_info.src_ip IS 'source ip';


--
-- Name: COLUMN config_info.tenant_id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.config_info.tenant_id IS '租户字段';


--
-- Name: COLUMN config_info.c_desc; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.config_info.c_desc IS '配置描述';


--
-- Name: COLUMN config_info.c_use; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.config_info.c_use IS '配置使用';


--
-- Name: COLUMN config_info.effect; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.config_info.effect IS '配置生效的描述';


--
-- Name: COLUMN config_info.type; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.config_info.type IS '配置的类型';


--
-- Name: COLUMN config_info.c_schema; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.config_info.c_schema IS '配置的模式';


--
-- Name: COLUMN config_info.encrypted_data_key; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.config_info.encrypted_data_key IS '秘钥';


--
-- Name: config_info_gray; Type: TABLE; Schema: public; Owner: faris
--

CREATE TABLE public.config_info_gray (
    id bigint NOT NULL,
    data_id character varying(255) NOT NULL,
    group_id character varying(128) NOT NULL,
    content text NOT NULL,
    md5 character varying(32),
    src_user text,
    src_ip character varying(100) NOT NULL,
    gmt_create timestamp(6) without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    gmt_modified timestamp(6) without time zone NOT NULL,
    app_name character varying(128) DEFAULT NULL::character varying,
    tenant_id character varying(128) DEFAULT ''::character varying,
    gray_name character varying(128) NOT NULL,
    gray_rule text NOT NULL,
    encrypted_data_key character varying(256) NOT NULL
);


ALTER TABLE public.config_info_gray OWNER TO faris;

--
-- Name: COLUMN config_info_gray.id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.config_info_gray.id IS '自增ID';


--
-- Name: COLUMN config_info_gray.data_id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.config_info_gray.data_id IS 'data_id';


--
-- Name: COLUMN config_info_gray.group_id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.config_info_gray.group_id IS 'group_id';


--
-- Name: COLUMN config_info_gray.content; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.config_info_gray.content IS 'content';


--
-- Name: COLUMN config_info_gray.md5; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.config_info_gray.md5 IS 'md5';


--
-- Name: COLUMN config_info_gray.src_user; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.config_info_gray.src_user IS 'src_user';


--
-- Name: COLUMN config_info_gray.src_ip; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.config_info_gray.src_ip IS 'src_ip';


--
-- Name: COLUMN config_info_gray.gmt_create; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.config_info_gray.gmt_create IS 'gmt_create';


--
-- Name: COLUMN config_info_gray.gmt_modified; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.config_info_gray.gmt_modified IS 'gmt_modified';


--
-- Name: COLUMN config_info_gray.app_name; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.config_info_gray.app_name IS 'app_name';


--
-- Name: COLUMN config_info_gray.tenant_id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.config_info_gray.tenant_id IS 'tenant_id';


--
-- Name: COLUMN config_info_gray.gray_name; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.config_info_gray.gray_name IS 'gray_name';


--
-- Name: COLUMN config_info_gray.gray_rule; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.config_info_gray.gray_rule IS 'gray_rule';


--
-- Name: COLUMN config_info_gray.encrypted_data_key; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.config_info_gray.encrypted_data_key IS 'encrypted_data_key';


--
-- Name: config_info_gray_id_seq; Type: SEQUENCE; Schema: public; Owner: faris
--

CREATE SEQUENCE public.config_info_gray_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.config_info_gray_id_seq OWNER TO faris;

--
-- Name: config_info_gray_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: faris
--

ALTER SEQUENCE public.config_info_gray_id_seq OWNED BY public.config_info_gray.id;


--
-- Name: config_info_id_seq; Type: SEQUENCE; Schema: public; Owner: faris
--

CREATE SEQUENCE public.config_info_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.config_info_id_seq OWNER TO faris;

--
-- Name: config_info_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: faris
--

ALTER SEQUENCE public.config_info_id_seq OWNED BY public.config_info.id;


--
-- Name: config_tags_relation; Type: TABLE; Schema: public; Owner: faris
--

CREATE TABLE public.config_tags_relation (
    id bigint NOT NULL,
    tag_name character varying(128) NOT NULL,
    tag_type character varying(64),
    data_id character varying(255) NOT NULL,
    group_id character varying(128) NOT NULL,
    tenant_id character varying(128),
    nid bigint NOT NULL
);


ALTER TABLE public.config_tags_relation OWNER TO faris;

--
-- Name: TABLE config_tags_relation; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON TABLE public.config_tags_relation IS 'config_tag_relation';


--
-- Name: COLUMN config_tags_relation.id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.config_tags_relation.id IS 'id';


--
-- Name: COLUMN config_tags_relation.tag_name; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.config_tags_relation.tag_name IS 'tag_name';


--
-- Name: COLUMN config_tags_relation.tag_type; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.config_tags_relation.tag_type IS 'tag_type';


--
-- Name: COLUMN config_tags_relation.data_id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.config_tags_relation.data_id IS 'data_id';


--
-- Name: COLUMN config_tags_relation.group_id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.config_tags_relation.group_id IS 'group_id';


--
-- Name: COLUMN config_tags_relation.tenant_id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.config_tags_relation.tenant_id IS 'tenant_id';


--
-- Name: COLUMN config_tags_relation.nid; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.config_tags_relation.nid IS '自增长标识';


--
-- Name: config_tags_relation_id_seq; Type: SEQUENCE; Schema: public; Owner: faris
--

CREATE SEQUENCE public.config_tags_relation_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.config_tags_relation_id_seq OWNER TO faris;

--
-- Name: config_tags_relation_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: faris
--

ALTER SEQUENCE public.config_tags_relation_id_seq OWNED BY public.config_tags_relation.id;


--
-- Name: config_tags_relation_nid_seq; Type: SEQUENCE; Schema: public; Owner: faris
--

CREATE SEQUENCE public.config_tags_relation_nid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.config_tags_relation_nid_seq OWNER TO faris;

--
-- Name: config_tags_relation_nid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: faris
--

ALTER SEQUENCE public.config_tags_relation_nid_seq OWNED BY public.config_tags_relation.nid;


--
-- Name: group_capacity; Type: TABLE; Schema: public; Owner: faris
--

CREATE TABLE public.group_capacity (
    id bigint NOT NULL,
    group_id character varying(128) NOT NULL,
    quota integer NOT NULL,
    usage integer NOT NULL,
    max_size integer NOT NULL,
    max_aggr_count integer NOT NULL,
    max_aggr_size integer NOT NULL,
    max_history_count integer NOT NULL,
    gmt_create timestamp(6) without time zone NOT NULL,
    gmt_modified timestamp(6) without time zone NOT NULL
);


ALTER TABLE public.group_capacity OWNER TO faris;

--
-- Name: TABLE group_capacity; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON TABLE public.group_capacity IS '集群、各Group容量信息表';


--
-- Name: COLUMN group_capacity.id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.group_capacity.id IS '主键ID';


--
-- Name: COLUMN group_capacity.group_id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.group_capacity.group_id IS 'Group ID，空字符表示整个集群';


--
-- Name: COLUMN group_capacity.quota; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.group_capacity.quota IS '配额，0表示使用默认值';


--
-- Name: COLUMN group_capacity.usage; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.group_capacity.usage IS '使用量';


--
-- Name: COLUMN group_capacity.max_size; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.group_capacity.max_size IS '单个配置大小上限，单位为字节，0表示使用默认值';


--
-- Name: COLUMN group_capacity.max_aggr_count; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.group_capacity.max_aggr_count IS '聚合子配置最大个数，，0表示使用默认值';


--
-- Name: COLUMN group_capacity.max_aggr_size; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.group_capacity.max_aggr_size IS '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值';


--
-- Name: COLUMN group_capacity.max_history_count; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.group_capacity.max_history_count IS '最大变更历史数量';


--
-- Name: COLUMN group_capacity.gmt_create; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.group_capacity.gmt_create IS '创建时间';


--
-- Name: COLUMN group_capacity.gmt_modified; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.group_capacity.gmt_modified IS '修改时间';


--
-- Name: group_capacity_id_seq; Type: SEQUENCE; Schema: public; Owner: faris
--

CREATE SEQUENCE public.group_capacity_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.group_capacity_id_seq OWNER TO faris;

--
-- Name: group_capacity_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: faris
--

ALTER SEQUENCE public.group_capacity_id_seq OWNED BY public.group_capacity.id;


--
-- Name: his_config_info; Type: TABLE; Schema: public; Owner: faris
--

CREATE TABLE public.his_config_info (
    id bigint NOT NULL,
    nid bigint NOT NULL,
    data_id character varying(255) NOT NULL,
    group_id character varying(128) NOT NULL,
    app_name character varying(128),
    content text NOT NULL,
    md5 character varying(32),
    gmt_create timestamp(6) without time zone DEFAULT '2010-05-05 00:00:00'::timestamp without time zone NOT NULL,
    gmt_modified timestamp(6) without time zone NOT NULL,
    src_user text,
    src_ip character varying(20),
    op_type character(10),
    tenant_id character varying(128),
    encrypted_data_key character varying(1024) NOT NULL,
    publish_type character varying(50) DEFAULT 'formal'::character varying,
    gray_name character varying(50),
    ext_info text
);


ALTER TABLE public.his_config_info OWNER TO faris;

--
-- Name: TABLE his_config_info; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON TABLE public.his_config_info IS '多租户改造';


--
-- Name: COLUMN his_config_info.nid; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.his_config_info.nid IS '自增标识';


--
-- Name: COLUMN his_config_info.app_name; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.his_config_info.app_name IS 'app_name';


--
-- Name: COLUMN his_config_info.tenant_id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.his_config_info.tenant_id IS '租户字段';


--
-- Name: COLUMN his_config_info.encrypted_data_key; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.his_config_info.encrypted_data_key IS '秘钥';


--
-- Name: COLUMN his_config_info.publish_type; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.his_config_info.publish_type IS 'publish type gray or formal';


--
-- Name: his_config_info_nid_seq; Type: SEQUENCE; Schema: public; Owner: faris
--

CREATE SEQUENCE public.his_config_info_nid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.his_config_info_nid_seq OWNER TO faris;

--
-- Name: his_config_info_nid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: faris
--

ALTER SEQUENCE public.his_config_info_nid_seq OWNED BY public.his_config_info.nid;


--
-- Name: permissions; Type: TABLE; Schema: public; Owner: faris
--

CREATE TABLE public.permissions (
    role character varying(50) NOT NULL,
    resource character varying(512) NOT NULL,
    action character varying(8) NOT NULL
);


ALTER TABLE public.permissions OWNER TO faris;

--
-- Name: roles; Type: TABLE; Schema: public; Owner: faris
--

CREATE TABLE public.roles (
    username character varying(50) NOT NULL,
    role character varying(50) NOT NULL
);


ALTER TABLE public.roles OWNER TO faris;

--
-- Name: tenant_capacity; Type: TABLE; Schema: public; Owner: faris
--

CREATE TABLE public.tenant_capacity (
    id bigint NOT NULL,
    tenant_id character varying(128) NOT NULL,
    quota integer NOT NULL,
    usage integer NOT NULL,
    max_size integer NOT NULL,
    max_aggr_count integer NOT NULL,
    max_aggr_size integer NOT NULL,
    max_history_count integer NOT NULL,
    gmt_create timestamp(6) without time zone NOT NULL,
    gmt_modified timestamp(6) without time zone NOT NULL
);


ALTER TABLE public.tenant_capacity OWNER TO faris;

--
-- Name: TABLE tenant_capacity; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON TABLE public.tenant_capacity IS '租户容量信息表';


--
-- Name: COLUMN tenant_capacity.id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.tenant_capacity.id IS '主键ID';


--
-- Name: COLUMN tenant_capacity.tenant_id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.tenant_capacity.tenant_id IS 'Tenant ID';


--
-- Name: COLUMN tenant_capacity.quota; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.tenant_capacity.quota IS '配额，0表示使用默认值';


--
-- Name: COLUMN tenant_capacity.usage; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.tenant_capacity.usage IS '使用量';


--
-- Name: COLUMN tenant_capacity.max_size; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.tenant_capacity.max_size IS '单个配置大小上限，单位为字节，0表示使用默认值';


--
-- Name: COLUMN tenant_capacity.max_aggr_count; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.tenant_capacity.max_aggr_count IS '聚合子配置最大个数';


--
-- Name: COLUMN tenant_capacity.max_aggr_size; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.tenant_capacity.max_aggr_size IS '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值';


--
-- Name: COLUMN tenant_capacity.max_history_count; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.tenant_capacity.max_history_count IS '最大变更历史数量';


--
-- Name: COLUMN tenant_capacity.gmt_create; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.tenant_capacity.gmt_create IS '创建时间';


--
-- Name: COLUMN tenant_capacity.gmt_modified; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.tenant_capacity.gmt_modified IS '修改时间';


--
-- Name: tenant_capacity_id_seq; Type: SEQUENCE; Schema: public; Owner: faris
--

CREATE SEQUENCE public.tenant_capacity_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.tenant_capacity_id_seq OWNER TO faris;

--
-- Name: tenant_capacity_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: faris
--

ALTER SEQUENCE public.tenant_capacity_id_seq OWNED BY public.tenant_capacity.id;


--
-- Name: tenant_info; Type: TABLE; Schema: public; Owner: faris
--

CREATE TABLE public.tenant_info (
    id bigint NOT NULL,
    kp character varying(128) NOT NULL,
    tenant_id character varying(128),
    tenant_name character varying(128),
    tenant_desc character varying(256),
    create_source character varying(32),
    gmt_create bigint NOT NULL,
    gmt_modified bigint NOT NULL
);


ALTER TABLE public.tenant_info OWNER TO faris;

--
-- Name: TABLE tenant_info; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON TABLE public.tenant_info IS 'tenant_info';


--
-- Name: COLUMN tenant_info.id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.tenant_info.id IS 'id';


--
-- Name: COLUMN tenant_info.kp; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.tenant_info.kp IS 'kp';


--
-- Name: COLUMN tenant_info.tenant_id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.tenant_info.tenant_id IS 'tenant_id';


--
-- Name: COLUMN tenant_info.tenant_name; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.tenant_info.tenant_name IS 'tenant_name';


--
-- Name: COLUMN tenant_info.tenant_desc; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.tenant_info.tenant_desc IS 'tenant_desc';


--
-- Name: COLUMN tenant_info.create_source; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.tenant_info.create_source IS 'create_source';


--
-- Name: COLUMN tenant_info.gmt_create; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.tenant_info.gmt_create IS '创建时间';


--
-- Name: COLUMN tenant_info.gmt_modified; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.tenant_info.gmt_modified IS '修改时间';


--
-- Name: tenant_info_id_seq; Type: SEQUENCE; Schema: public; Owner: faris
--

CREATE SEQUENCE public.tenant_info_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.tenant_info_id_seq OWNER TO faris;

--
-- Name: tenant_info_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: faris
--

ALTER SEQUENCE public.tenant_info_id_seq OWNED BY public.tenant_info.id;


--
-- Name: users; Type: TABLE; Schema: public; Owner: faris
--

CREATE TABLE public.users (
    username character varying(50) NOT NULL,
    password character varying(500) NOT NULL,
    enabled boolean NOT NULL
);


ALTER TABLE public.users OWNER TO faris;

--
-- Name: config_info id; Type: DEFAULT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.config_info ALTER COLUMN id SET DEFAULT nextval('public.config_info_id_seq'::regclass);


--
-- Name: config_info_gray id; Type: DEFAULT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.config_info_gray ALTER COLUMN id SET DEFAULT nextval('public.config_info_gray_id_seq'::regclass);


--
-- Name: config_tags_relation id; Type: DEFAULT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.config_tags_relation ALTER COLUMN id SET DEFAULT nextval('public.config_tags_relation_id_seq'::regclass);


--
-- Name: config_tags_relation nid; Type: DEFAULT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.config_tags_relation ALTER COLUMN nid SET DEFAULT nextval('public.config_tags_relation_nid_seq'::regclass);


--
-- Name: group_capacity id; Type: DEFAULT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.group_capacity ALTER COLUMN id SET DEFAULT nextval('public.group_capacity_id_seq'::regclass);


--
-- Name: his_config_info nid; Type: DEFAULT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.his_config_info ALTER COLUMN nid SET DEFAULT nextval('public.his_config_info_nid_seq'::regclass);


--
-- Name: tenant_capacity id; Type: DEFAULT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.tenant_capacity ALTER COLUMN id SET DEFAULT nextval('public.tenant_capacity_id_seq'::regclass);


--
-- Name: tenant_info id; Type: DEFAULT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.tenant_info ALTER COLUMN id SET DEFAULT nextval('public.tenant_info_id_seq'::regclass);


--
-- Data for Name: config_info; Type: TABLE DATA; Schema: public; Owner: faris
--

COPY public.config_info (id, data_id, group_id, content, md5, gmt_create, gmt_modified, src_user, src_ip, app_name, tenant_id, c_desc, c_use, effect, type, c_schema, encrypted_data_key) FROM stdin;
\.


--
-- Data for Name: config_info_gray; Type: TABLE DATA; Schema: public; Owner: faris
--

COPY public.config_info_gray (id, data_id, group_id, content, md5, src_user, src_ip, gmt_create, gmt_modified, app_name, tenant_id, gray_name, gray_rule, encrypted_data_key) FROM stdin;
\.


--
-- Data for Name: config_tags_relation; Type: TABLE DATA; Schema: public; Owner: faris
--

COPY public.config_tags_relation (id, tag_name, tag_type, data_id, group_id, tenant_id, nid) FROM stdin;
\.


--
-- Data for Name: group_capacity; Type: TABLE DATA; Schema: public; Owner: faris
--

COPY public.group_capacity (id, group_id, quota, usage, max_size, max_aggr_count, max_aggr_size, max_history_count, gmt_create, gmt_modified) FROM stdin;
\.


--
-- Data for Name: his_config_info; Type: TABLE DATA; Schema: public; Owner: faris
--

COPY public.his_config_info (id, nid, data_id, group_id, app_name, content, md5, gmt_create, gmt_modified, src_user, src_ip, op_type, tenant_id, encrypted_data_key, publish_type, gray_name, ext_info) FROM stdin;
\.


--
-- Data for Name: permissions; Type: TABLE DATA; Schema: public; Owner: faris
--

COPY public.permissions (role, resource, action) FROM stdin;
\.


--
-- Data for Name: roles; Type: TABLE DATA; Schema: public; Owner: faris
--

COPY public.roles (username, role) FROM stdin;
nacos	ROLE_ADMIN
\.


--
-- Data for Name: tenant_capacity; Type: TABLE DATA; Schema: public; Owner: faris
--

COPY public.tenant_capacity (id, tenant_id, quota, usage, max_size, max_aggr_count, max_aggr_size, max_history_count, gmt_create, gmt_modified) FROM stdin;
\.


--
-- Data for Name: tenant_info; Type: TABLE DATA; Schema: public; Owner: faris
--

COPY public.tenant_info (id, kp, tenant_id, tenant_name, tenant_desc, create_source, gmt_create, gmt_modified) FROM stdin;
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: faris
--

COPY public.users (username, password, enabled) FROM stdin;
nacos	$2a$10$EuWPZHzz32dJN7jexM34MOeYirDdFAZm2kuWj7VEOJhhZkDrxfvUu	t
\.


--
-- Name: config_info_gray_id_seq; Type: SEQUENCE SET; Schema: public; Owner: faris
--

SELECT pg_catalog.setval('public.config_info_gray_id_seq', 1, false);


--
-- Name: config_info_id_seq; Type: SEQUENCE SET; Schema: public; Owner: faris
--

SELECT pg_catalog.setval('public.config_info_id_seq', 1, false);


--
-- Name: config_tags_relation_id_seq; Type: SEQUENCE SET; Schema: public; Owner: faris
--

SELECT pg_catalog.setval('public.config_tags_relation_id_seq', 1, false);


--
-- Name: config_tags_relation_nid_seq; Type: SEQUENCE SET; Schema: public; Owner: faris
--

SELECT pg_catalog.setval('public.config_tags_relation_nid_seq', 1, false);


--
-- Name: group_capacity_id_seq; Type: SEQUENCE SET; Schema: public; Owner: faris
--

SELECT pg_catalog.setval('public.group_capacity_id_seq', 1, false);


--
-- Name: his_config_info_nid_seq; Type: SEQUENCE SET; Schema: public; Owner: faris
--

SELECT pg_catalog.setval('public.his_config_info_nid_seq', 1, false);


--
-- Name: tenant_capacity_id_seq; Type: SEQUENCE SET; Schema: public; Owner: faris
--

SELECT pg_catalog.setval('public.tenant_capacity_id_seq', 1, false);


--
-- Name: tenant_info_id_seq; Type: SEQUENCE SET; Schema: public; Owner: faris
--

SELECT pg_catalog.setval('public.tenant_info_id_seq', 1, false);


--
-- Name: config_info_gray config_info_gray_pkey; Type: CONSTRAINT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.config_info_gray
    ADD CONSTRAINT config_info_gray_pkey PRIMARY KEY (id);


--
-- Name: config_info config_info_pkey; Type: CONSTRAINT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.config_info
    ADD CONSTRAINT config_info_pkey PRIMARY KEY (id);


--
-- Name: config_tags_relation config_tags_relation_pkey; Type: CONSTRAINT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.config_tags_relation
    ADD CONSTRAINT config_tags_relation_pkey PRIMARY KEY (nid);


--
-- Name: group_capacity group_capacity_pkey; Type: CONSTRAINT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.group_capacity
    ADD CONSTRAINT group_capacity_pkey PRIMARY KEY (id);


--
-- Name: his_config_info his_config_info_pkey; Type: CONSTRAINT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.his_config_info
    ADD CONSTRAINT his_config_info_pkey PRIMARY KEY (nid);


--
-- Name: tenant_capacity tenant_capacity_pkey; Type: CONSTRAINT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.tenant_capacity
    ADD CONSTRAINT tenant_capacity_pkey PRIMARY KEY (id);


--
-- Name: idx_did; Type: INDEX; Schema: public; Owner: faris
--

CREATE INDEX idx_did ON public.his_config_info USING btree (data_id);


--
-- Name: idx_gmt_create; Type: INDEX; Schema: public; Owner: faris
--

CREATE INDEX idx_gmt_create ON public.his_config_info USING btree (gmt_create);


--
-- Name: idx_gmt_modified; Type: INDEX; Schema: public; Owner: faris
--

CREATE INDEX idx_gmt_modified ON public.his_config_info USING btree (gmt_modified);


--
-- Name: idx_tenant_id; Type: INDEX; Schema: public; Owner: faris
--

CREATE INDEX idx_tenant_id ON public.config_tags_relation USING btree (tenant_id);


--
-- Name: uk_configinfo_datagrouptenant; Type: INDEX; Schema: public; Owner: faris
--

CREATE UNIQUE INDEX uk_configinfo_datagrouptenant ON public.config_info USING btree (data_id, group_id, tenant_id);


--
-- Name: uk_configtagrelation_configidtag; Type: INDEX; Schema: public; Owner: faris
--

CREATE UNIQUE INDEX uk_configtagrelation_configidtag ON public.config_tags_relation USING btree (id, tag_name, tag_type);


--
-- Name: uk_group_id; Type: INDEX; Schema: public; Owner: faris
--

CREATE UNIQUE INDEX uk_group_id ON public.group_capacity USING btree (group_id);


--
-- Name: uk_role_permission; Type: INDEX; Schema: public; Owner: faris
--

CREATE UNIQUE INDEX uk_role_permission ON public.permissions USING btree (role, resource, action);


--
-- Name: uk_tenant_id; Type: INDEX; Schema: public; Owner: faris
--

CREATE UNIQUE INDEX uk_tenant_id ON public.tenant_capacity USING btree (tenant_id);


--
-- Name: uk_tenant_info_kptenantid; Type: INDEX; Schema: public; Owner: faris
--

CREATE UNIQUE INDEX uk_tenant_info_kptenantid ON public.tenant_info USING btree (kp, tenant_id);


--
-- Name: uk_username_role; Type: INDEX; Schema: public; Owner: faris
--

CREATE UNIQUE INDEX uk_username_role ON public.roles USING btree (username, role);


--
-- PostgreSQL database dump complete
--

\unrestrict bS8Oh0sIYbe4OcPTgIuDSkIpePD6dapQUzP11sO4f48WufhplCho3L4fBnr7FfU

