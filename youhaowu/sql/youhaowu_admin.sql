--
-- PostgreSQL database dump
--

\restrict WPcUjOluzYBbe9WlZZjaJoClGyOV2xt4LqAWG52AK8uRk22DJTPMT5NYQkZotOY

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
-- Name: qrtz_blob_triggers; Type: TABLE; Schema: public; Owner: faris
--

CREATE TABLE public.qrtz_blob_triggers (
    sched_name character varying(120) NOT NULL,
    trigger_name character varying(200) NOT NULL,
    trigger_group character varying(200) NOT NULL,
    blob_data bytea
);


ALTER TABLE public.qrtz_blob_triggers OWNER TO faris;

--
-- Name: qrtz_calendars; Type: TABLE; Schema: public; Owner: faris
--

CREATE TABLE public.qrtz_calendars (
    sched_name character varying(120) NOT NULL,
    calendar_name character varying(200) NOT NULL,
    calendar bytea NOT NULL
);


ALTER TABLE public.qrtz_calendars OWNER TO faris;

--
-- Name: qrtz_cron_triggers; Type: TABLE; Schema: public; Owner: faris
--

CREATE TABLE public.qrtz_cron_triggers (
    sched_name character varying(120) NOT NULL,
    trigger_name character varying(200) NOT NULL,
    trigger_group character varying(200) NOT NULL,
    cron_expression character varying(120) NOT NULL,
    time_zone_id character varying(80)
);


ALTER TABLE public.qrtz_cron_triggers OWNER TO faris;

--
-- Name: qrtz_fired_triggers; Type: TABLE; Schema: public; Owner: faris
--

CREATE TABLE public.qrtz_fired_triggers (
    sched_name character varying(120) NOT NULL,
    entry_id character varying(95) NOT NULL,
    trigger_name character varying(200) NOT NULL,
    trigger_group character varying(200) NOT NULL,
    instance_name character varying(200) NOT NULL,
    fired_time bigint NOT NULL,
    sched_time bigint NOT NULL,
    priority integer NOT NULL,
    state character varying(16) NOT NULL,
    job_name character varying(200),
    job_group character varying(200),
    is_nonconcurrent character varying(1),
    requests_recovery character varying(1)
);


ALTER TABLE public.qrtz_fired_triggers OWNER TO faris;

--
-- Name: qrtz_job_details; Type: TABLE; Schema: public; Owner: faris
--

CREATE TABLE public.qrtz_job_details (
    sched_name character varying(120) NOT NULL,
    job_name character varying(200) NOT NULL,
    job_group character varying(200) NOT NULL,
    description character varying(250),
    job_class_name character varying(250) NOT NULL,
    is_durable character varying(1) NOT NULL,
    is_nonconcurrent character varying(1) NOT NULL,
    is_update_data character varying(1) NOT NULL,
    requests_recovery character varying(1) NOT NULL,
    job_data bytea
);


ALTER TABLE public.qrtz_job_details OWNER TO faris;

--
-- Name: qrtz_locks; Type: TABLE; Schema: public; Owner: faris
--

CREATE TABLE public.qrtz_locks (
    sched_name character varying(120) NOT NULL,
    lock_name character varying(40) NOT NULL
);


ALTER TABLE public.qrtz_locks OWNER TO faris;

--
-- Name: qrtz_paused_trigger_grps; Type: TABLE; Schema: public; Owner: faris
--

CREATE TABLE public.qrtz_paused_trigger_grps (
    sched_name character varying(120) NOT NULL,
    trigger_group character varying(200) NOT NULL
);


ALTER TABLE public.qrtz_paused_trigger_grps OWNER TO faris;

--
-- Name: qrtz_scheduler_state; Type: TABLE; Schema: public; Owner: faris
--

CREATE TABLE public.qrtz_scheduler_state (
    sched_name character varying(120) NOT NULL,
    instance_name character varying(200) NOT NULL,
    last_checkin_time bigint NOT NULL,
    checkin_interval bigint NOT NULL
);


ALTER TABLE public.qrtz_scheduler_state OWNER TO faris;

--
-- Name: qrtz_simple_triggers; Type: TABLE; Schema: public; Owner: faris
--

CREATE TABLE public.qrtz_simple_triggers (
    sched_name character varying(120) NOT NULL,
    trigger_name character varying(200) NOT NULL,
    trigger_group character varying(200) NOT NULL,
    repeat_count bigint NOT NULL,
    repeat_interval bigint NOT NULL,
    times_triggered bigint NOT NULL
);


ALTER TABLE public.qrtz_simple_triggers OWNER TO faris;

--
-- Name: qrtz_simprop_triggers; Type: TABLE; Schema: public; Owner: faris
--

CREATE TABLE public.qrtz_simprop_triggers (
    sched_name character varying(120) NOT NULL,
    trigger_name character varying(200) NOT NULL,
    trigger_group character varying(200) NOT NULL,
    str_prop_1 character varying(512),
    str_prop_2 character varying(512),
    str_prop_3 character varying(512),
    int_prop_1 integer,
    int_prop_2 integer,
    long_prop_1 bigint,
    long_prop_2 bigint,
    dec_prop_1 numeric(13,4),
    dec_prop_2 numeric(13,4),
    bool_prop_1 character varying(1),
    bool_prop_2 character varying(1)
);


ALTER TABLE public.qrtz_simprop_triggers OWNER TO faris;

--
-- Name: qrtz_triggers; Type: TABLE; Schema: public; Owner: faris
--

CREATE TABLE public.qrtz_triggers (
    sched_name character varying(120) NOT NULL,
    trigger_name character varying(200) NOT NULL,
    trigger_group character varying(200) NOT NULL,
    job_name character varying(200) NOT NULL,
    job_group character varying(200) NOT NULL,
    description character varying(250),
    next_fire_time bigint,
    prev_fire_time bigint,
    priority integer,
    trigger_state character varying(16) NOT NULL,
    trigger_type character varying(8) NOT NULL,
    start_time bigint NOT NULL,
    end_time bigint,
    calendar_name character varying(200),
    misfire_instr smallint,
    job_data bytea
);


ALTER TABLE public.qrtz_triggers OWNER TO faris;

--
-- Name: schedule_job; Type: TABLE; Schema: public; Owner: faris
--

CREATE TABLE public.schedule_job (
    job_id bigint NOT NULL,
    bean_name character varying(200),
    params character varying(2000),
    cron_expression character varying(100),
    status smallint,
    remark character varying(255),
    create_time timestamp with time zone
);


ALTER TABLE public.schedule_job OWNER TO faris;

--
-- Name: TABLE schedule_job; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON TABLE public.schedule_job IS '定时任务';


--
-- Name: COLUMN schedule_job.job_id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.schedule_job.job_id IS '任务id';


--
-- Name: COLUMN schedule_job.bean_name; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.schedule_job.bean_name IS 'spring bean名称';


--
-- Name: COLUMN schedule_job.params; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.schedule_job.params IS '参数';


--
-- Name: COLUMN schedule_job.cron_expression; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.schedule_job.cron_expression IS 'cron表达式';


--
-- Name: COLUMN schedule_job.status; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.schedule_job.status IS '任务状态  0：正常  1：暂停';


--
-- Name: COLUMN schedule_job.remark; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.schedule_job.remark IS '备注';


--
-- Name: COLUMN schedule_job.create_time; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.schedule_job.create_time IS '创建时间';


--
-- Name: schedule_job_job_id_seq; Type: SEQUENCE; Schema: public; Owner: faris
--

CREATE SEQUENCE public.schedule_job_job_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.schedule_job_job_id_seq OWNER TO faris;

--
-- Name: schedule_job_job_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: faris
--

ALTER SEQUENCE public.schedule_job_job_id_seq OWNED BY public.schedule_job.job_id;


--
-- Name: schedule_job_log; Type: TABLE; Schema: public; Owner: faris
--

CREATE TABLE public.schedule_job_log (
    log_id bigint NOT NULL,
    job_id bigint NOT NULL,
    bean_name character varying(200),
    params character varying(2000),
    status smallint NOT NULL,
    error character varying(2000),
    times integer NOT NULL,
    create_time timestamp with time zone
);


ALTER TABLE public.schedule_job_log OWNER TO faris;

--
-- Name: TABLE schedule_job_log; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON TABLE public.schedule_job_log IS '定时任务日志';


--
-- Name: COLUMN schedule_job_log.log_id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.schedule_job_log.log_id IS '任务日志id';


--
-- Name: COLUMN schedule_job_log.job_id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.schedule_job_log.job_id IS '任务id';


--
-- Name: COLUMN schedule_job_log.bean_name; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.schedule_job_log.bean_name IS 'spring bean名称';


--
-- Name: COLUMN schedule_job_log.params; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.schedule_job_log.params IS '参数';


--
-- Name: COLUMN schedule_job_log.status; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.schedule_job_log.status IS '任务状态    0：成功    1：失败';


--
-- Name: COLUMN schedule_job_log.error; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.schedule_job_log.error IS '失败信息';


--
-- Name: COLUMN schedule_job_log.times; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.schedule_job_log.times IS '耗时(单位：毫秒)';


--
-- Name: COLUMN schedule_job_log.create_time; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.schedule_job_log.create_time IS '创建时间';


--
-- Name: schedule_job_log_log_id_seq; Type: SEQUENCE; Schema: public; Owner: faris
--

CREATE SEQUENCE public.schedule_job_log_log_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.schedule_job_log_log_id_seq OWNER TO faris;

--
-- Name: schedule_job_log_log_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: faris
--

ALTER SEQUENCE public.schedule_job_log_log_id_seq OWNED BY public.schedule_job_log.log_id;


--
-- Name: sys_captcha; Type: TABLE; Schema: public; Owner: faris
--

CREATE TABLE public.sys_captcha (
    uuid character varying(36) NOT NULL,
    code character varying(6) NOT NULL,
    expire_time timestamp with time zone
);


ALTER TABLE public.sys_captcha OWNER TO faris;

--
-- Name: TABLE sys_captcha; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON TABLE public.sys_captcha IS '系统验证码';


--
-- Name: COLUMN sys_captcha.uuid; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sys_captcha.uuid IS 'uuid';


--
-- Name: COLUMN sys_captcha.code; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sys_captcha.code IS '验证码';


--
-- Name: COLUMN sys_captcha.expire_time; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sys_captcha.expire_time IS '过期时间';


--
-- Name: sys_config; Type: TABLE; Schema: public; Owner: faris
--

CREATE TABLE public.sys_config (
    id bigint NOT NULL,
    param_key character varying(50),
    param_value character varying(2000),
    status smallint DEFAULT '1'::smallint,
    remark character varying(500)
);


ALTER TABLE public.sys_config OWNER TO faris;

--
-- Name: TABLE sys_config; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON TABLE public.sys_config IS '系统配置信息表';


--
-- Name: COLUMN sys_config.param_key; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sys_config.param_key IS 'key';


--
-- Name: COLUMN sys_config.param_value; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sys_config.param_value IS 'value';


--
-- Name: COLUMN sys_config.status; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sys_config.status IS '状态   0：隐藏   1：显示';


--
-- Name: COLUMN sys_config.remark; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sys_config.remark IS '备注';


--
-- Name: sys_config_id_seq; Type: SEQUENCE; Schema: public; Owner: faris
--

CREATE SEQUENCE public.sys_config_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.sys_config_id_seq OWNER TO faris;

--
-- Name: sys_config_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: faris
--

ALTER SEQUENCE public.sys_config_id_seq OWNED BY public.sys_config.id;


--
-- Name: sys_log; Type: TABLE; Schema: public; Owner: faris
--

CREATE TABLE public.sys_log (
    id bigint NOT NULL,
    username character varying(50),
    operation character varying(50),
    method character varying(200),
    params character varying(5000),
    "time" bigint NOT NULL,
    ip character varying(64),
    create_date timestamp with time zone
);


ALTER TABLE public.sys_log OWNER TO faris;

--
-- Name: TABLE sys_log; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON TABLE public.sys_log IS '系统日志';


--
-- Name: COLUMN sys_log.username; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sys_log.username IS '用户名';


--
-- Name: COLUMN sys_log.operation; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sys_log.operation IS '用户操作';


--
-- Name: COLUMN sys_log.method; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sys_log.method IS '请求方法';


--
-- Name: COLUMN sys_log.params; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sys_log.params IS '请求参数';


--
-- Name: COLUMN sys_log."time"; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sys_log."time" IS '执行时长(毫秒)';


--
-- Name: COLUMN sys_log.ip; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sys_log.ip IS 'IP地址';


--
-- Name: COLUMN sys_log.create_date; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sys_log.create_date IS '创建时间';


--
-- Name: sys_log_id_seq; Type: SEQUENCE; Schema: public; Owner: faris
--

CREATE SEQUENCE public.sys_log_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.sys_log_id_seq OWNER TO faris;

--
-- Name: sys_log_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: faris
--

ALTER SEQUENCE public.sys_log_id_seq OWNED BY public.sys_log.id;


--
-- Name: sys_menu; Type: TABLE; Schema: public; Owner: faris
--

CREATE TABLE public.sys_menu (
    menu_id bigint NOT NULL,
    parent_id bigint,
    name character varying(50),
    url character varying(200),
    perms character varying(500),
    type integer,
    icon character varying(50),
    order_num integer
);


ALTER TABLE public.sys_menu OWNER TO faris;

--
-- Name: TABLE sys_menu; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON TABLE public.sys_menu IS '菜单管理';


--
-- Name: COLUMN sys_menu.parent_id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sys_menu.parent_id IS '父菜单ID，一级菜单为0';


--
-- Name: COLUMN sys_menu.name; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sys_menu.name IS '菜单名称';


--
-- Name: COLUMN sys_menu.url; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sys_menu.url IS '菜单URL';


--
-- Name: COLUMN sys_menu.perms; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sys_menu.perms IS '授权(多个用逗号分隔，如：user:list,user:create)';


--
-- Name: COLUMN sys_menu.type; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sys_menu.type IS '类型   0：目录   1：菜单   2：按钮';


--
-- Name: COLUMN sys_menu.icon; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sys_menu.icon IS '菜单图标';


--
-- Name: COLUMN sys_menu.order_num; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sys_menu.order_num IS '排序';


--
-- Name: sys_menu_menu_id_seq; Type: SEQUENCE; Schema: public; Owner: faris
--

CREATE SEQUENCE public.sys_menu_menu_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.sys_menu_menu_id_seq OWNER TO faris;

--
-- Name: sys_menu_menu_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: faris
--

ALTER SEQUENCE public.sys_menu_menu_id_seq OWNED BY public.sys_menu.menu_id;


--
-- Name: sys_oss; Type: TABLE; Schema: public; Owner: faris
--

CREATE TABLE public.sys_oss (
    id bigint NOT NULL,
    url character varying(200),
    create_date timestamp with time zone
);


ALTER TABLE public.sys_oss OWNER TO faris;

--
-- Name: TABLE sys_oss; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON TABLE public.sys_oss IS '文件上传';


--
-- Name: COLUMN sys_oss.url; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sys_oss.url IS 'URL地址';


--
-- Name: COLUMN sys_oss.create_date; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sys_oss.create_date IS '创建时间';


--
-- Name: sys_oss_id_seq; Type: SEQUENCE; Schema: public; Owner: faris
--

CREATE SEQUENCE public.sys_oss_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.sys_oss_id_seq OWNER TO faris;

--
-- Name: sys_oss_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: faris
--

ALTER SEQUENCE public.sys_oss_id_seq OWNED BY public.sys_oss.id;


--
-- Name: sys_role; Type: TABLE; Schema: public; Owner: faris
--

CREATE TABLE public.sys_role (
    role_id bigint NOT NULL,
    role_name character varying(100),
    remark character varying(100),
    create_user_id bigint,
    create_time timestamp with time zone
);


ALTER TABLE public.sys_role OWNER TO faris;

--
-- Name: TABLE sys_role; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON TABLE public.sys_role IS '角色';


--
-- Name: COLUMN sys_role.role_name; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sys_role.role_name IS '角色名称';


--
-- Name: COLUMN sys_role.remark; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sys_role.remark IS '备注';


--
-- Name: COLUMN sys_role.create_user_id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sys_role.create_user_id IS '创建者ID';


--
-- Name: COLUMN sys_role.create_time; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sys_role.create_time IS '创建时间';


--
-- Name: sys_role_menu; Type: TABLE; Schema: public; Owner: faris
--

CREATE TABLE public.sys_role_menu (
    id bigint NOT NULL,
    role_id bigint,
    menu_id bigint
);


ALTER TABLE public.sys_role_menu OWNER TO faris;

--
-- Name: TABLE sys_role_menu; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON TABLE public.sys_role_menu IS '角色与菜单对应关系';


--
-- Name: COLUMN sys_role_menu.role_id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sys_role_menu.role_id IS '角色ID';


--
-- Name: COLUMN sys_role_menu.menu_id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sys_role_menu.menu_id IS '菜单ID';


--
-- Name: sys_role_menu_id_seq; Type: SEQUENCE; Schema: public; Owner: faris
--

CREATE SEQUENCE public.sys_role_menu_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.sys_role_menu_id_seq OWNER TO faris;

--
-- Name: sys_role_menu_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: faris
--

ALTER SEQUENCE public.sys_role_menu_id_seq OWNED BY public.sys_role_menu.id;


--
-- Name: sys_role_role_id_seq; Type: SEQUENCE; Schema: public; Owner: faris
--

CREATE SEQUENCE public.sys_role_role_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.sys_role_role_id_seq OWNER TO faris;

--
-- Name: sys_role_role_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: faris
--

ALTER SEQUENCE public.sys_role_role_id_seq OWNED BY public.sys_role.role_id;


--
-- Name: sys_user; Type: TABLE; Schema: public; Owner: faris
--

CREATE TABLE public.sys_user (
    user_id bigint NOT NULL,
    username character varying(50) NOT NULL,
    password character varying(100),
    salt character varying(20),
    email character varying(100),
    mobile character varying(100),
    status smallint,
    create_user_id bigint,
    create_time timestamp with time zone
);


ALTER TABLE public.sys_user OWNER TO faris;

--
-- Name: TABLE sys_user; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON TABLE public.sys_user IS '系统用户';


--
-- Name: COLUMN sys_user.username; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sys_user.username IS '用户名';


--
-- Name: COLUMN sys_user.password; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sys_user.password IS '密码';


--
-- Name: COLUMN sys_user.salt; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sys_user.salt IS '盐';


--
-- Name: COLUMN sys_user.email; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sys_user.email IS '邮箱';


--
-- Name: COLUMN sys_user.mobile; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sys_user.mobile IS '手机号';


--
-- Name: COLUMN sys_user.status; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sys_user.status IS '状态  0：禁用   1：正常';


--
-- Name: COLUMN sys_user.create_user_id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sys_user.create_user_id IS '创建者ID';


--
-- Name: COLUMN sys_user.create_time; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sys_user.create_time IS '创建时间';


--
-- Name: sys_user_role; Type: TABLE; Schema: public; Owner: faris
--

CREATE TABLE public.sys_user_role (
    id bigint NOT NULL,
    user_id bigint,
    role_id bigint
);


ALTER TABLE public.sys_user_role OWNER TO faris;

--
-- Name: TABLE sys_user_role; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON TABLE public.sys_user_role IS '用户与角色对应关系';


--
-- Name: COLUMN sys_user_role.user_id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sys_user_role.user_id IS '用户ID';


--
-- Name: COLUMN sys_user_role.role_id; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sys_user_role.role_id IS '角色ID';


--
-- Name: sys_user_role_id_seq; Type: SEQUENCE; Schema: public; Owner: faris
--

CREATE SEQUENCE public.sys_user_role_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.sys_user_role_id_seq OWNER TO faris;

--
-- Name: sys_user_role_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: faris
--

ALTER SEQUENCE public.sys_user_role_id_seq OWNED BY public.sys_user_role.id;


--
-- Name: sys_user_token; Type: TABLE; Schema: public; Owner: faris
--

CREATE TABLE public.sys_user_token (
    user_id bigint NOT NULL,
    token character varying(100) NOT NULL,
    expire_time timestamp with time zone,
    update_time timestamp with time zone
);


ALTER TABLE public.sys_user_token OWNER TO faris;

--
-- Name: TABLE sys_user_token; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON TABLE public.sys_user_token IS '系统用户Token';


--
-- Name: COLUMN sys_user_token.token; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sys_user_token.token IS 'token';


--
-- Name: COLUMN sys_user_token.expire_time; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sys_user_token.expire_time IS '过期时间';


--
-- Name: COLUMN sys_user_token.update_time; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.sys_user_token.update_time IS '更新时间';


--
-- Name: sys_user_user_id_seq; Type: SEQUENCE; Schema: public; Owner: faris
--

CREATE SEQUENCE public.sys_user_user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.sys_user_user_id_seq OWNER TO faris;

--
-- Name: sys_user_user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: faris
--

ALTER SEQUENCE public.sys_user_user_id_seq OWNED BY public.sys_user.user_id;


--
-- Name: tb_user; Type: TABLE; Schema: public; Owner: faris
--

CREATE TABLE public.tb_user (
    user_id bigint NOT NULL,
    username character varying(50) NOT NULL,
    mobile character varying(20) NOT NULL,
    password character varying(64),
    create_time timestamp with time zone
);


ALTER TABLE public.tb_user OWNER TO faris;

--
-- Name: TABLE tb_user; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON TABLE public.tb_user IS '用户';


--
-- Name: COLUMN tb_user.username; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.tb_user.username IS '用户名';


--
-- Name: COLUMN tb_user.mobile; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.tb_user.mobile IS '手机号';


--
-- Name: COLUMN tb_user.password; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.tb_user.password IS '密码';


--
-- Name: COLUMN tb_user.create_time; Type: COMMENT; Schema: public; Owner: faris
--

COMMENT ON COLUMN public.tb_user.create_time IS '创建时间';


--
-- Name: tb_user_user_id_seq; Type: SEQUENCE; Schema: public; Owner: faris
--

CREATE SEQUENCE public.tb_user_user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.tb_user_user_id_seq OWNER TO faris;

--
-- Name: tb_user_user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: faris
--

ALTER SEQUENCE public.tb_user_user_id_seq OWNED BY public.tb_user.user_id;


--
-- Name: undo_log; Type: TABLE; Schema: public; Owner: faris
--

CREATE TABLE public.undo_log (
    id bigint NOT NULL,
    branch_id bigint NOT NULL,
    xid character varying(100) NOT NULL,
    context character varying(128) NOT NULL,
    rollback_info bytea NOT NULL,
    log_status integer NOT NULL,
    log_created timestamp with time zone NOT NULL,
    log_modified timestamp with time zone NOT NULL,
    ext character varying(100)
);


ALTER TABLE public.undo_log OWNER TO faris;

--
-- Name: undo_log_id_seq; Type: SEQUENCE; Schema: public; Owner: faris
--

CREATE SEQUENCE public.undo_log_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.undo_log_id_seq OWNER TO faris;

--
-- Name: undo_log_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: faris
--

ALTER SEQUENCE public.undo_log_id_seq OWNED BY public.undo_log.id;


--
-- Name: schedule_job job_id; Type: DEFAULT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.schedule_job ALTER COLUMN job_id SET DEFAULT nextval('public.schedule_job_job_id_seq'::regclass);


--
-- Name: schedule_job_log log_id; Type: DEFAULT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.schedule_job_log ALTER COLUMN log_id SET DEFAULT nextval('public.schedule_job_log_log_id_seq'::regclass);


--
-- Name: sys_config id; Type: DEFAULT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.sys_config ALTER COLUMN id SET DEFAULT nextval('public.sys_config_id_seq'::regclass);


--
-- Name: sys_log id; Type: DEFAULT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.sys_log ALTER COLUMN id SET DEFAULT nextval('public.sys_log_id_seq'::regclass);


--
-- Name: sys_menu menu_id; Type: DEFAULT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.sys_menu ALTER COLUMN menu_id SET DEFAULT nextval('public.sys_menu_menu_id_seq'::regclass);


--
-- Name: sys_oss id; Type: DEFAULT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.sys_oss ALTER COLUMN id SET DEFAULT nextval('public.sys_oss_id_seq'::regclass);


--
-- Name: sys_role role_id; Type: DEFAULT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.sys_role ALTER COLUMN role_id SET DEFAULT nextval('public.sys_role_role_id_seq'::regclass);


--
-- Name: sys_role_menu id; Type: DEFAULT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.sys_role_menu ALTER COLUMN id SET DEFAULT nextval('public.sys_role_menu_id_seq'::regclass);


--
-- Name: sys_user user_id; Type: DEFAULT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.sys_user ALTER COLUMN user_id SET DEFAULT nextval('public.sys_user_user_id_seq'::regclass);


--
-- Name: sys_user_role id; Type: DEFAULT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.sys_user_role ALTER COLUMN id SET DEFAULT nextval('public.sys_user_role_id_seq'::regclass);


--
-- Name: tb_user user_id; Type: DEFAULT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.tb_user ALTER COLUMN user_id SET DEFAULT nextval('public.tb_user_user_id_seq'::regclass);


--
-- Name: undo_log id; Type: DEFAULT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.undo_log ALTER COLUMN id SET DEFAULT nextval('public.undo_log_id_seq'::regclass);


--
-- Data for Name: qrtz_blob_triggers; Type: TABLE DATA; Schema: public; Owner: faris
--

COPY public.qrtz_blob_triggers (sched_name, trigger_name, trigger_group, blob_data) FROM stdin;
\.


--
-- Data for Name: qrtz_calendars; Type: TABLE DATA; Schema: public; Owner: faris
--

COPY public.qrtz_calendars (sched_name, calendar_name, calendar) FROM stdin;
\.


--
-- Data for Name: qrtz_cron_triggers; Type: TABLE DATA; Schema: public; Owner: faris
--

COPY public.qrtz_cron_triggers (sched_name, trigger_name, trigger_group, cron_expression, time_zone_id) FROM stdin;
RenrenScheduler	TASK_1	DEFAULT	0 0/30 * * * ?	Etc/UTC
\.


--
-- Data for Name: qrtz_fired_triggers; Type: TABLE DATA; Schema: public; Owner: faris
--

COPY public.qrtz_fired_triggers (sched_name, entry_id, trigger_name, trigger_group, instance_name, fired_time, sched_time, priority, state, job_name, job_group, is_nonconcurrent, requests_recovery) FROM stdin;
\.


--
-- Data for Name: qrtz_job_details; Type: TABLE DATA; Schema: public; Owner: faris
--

COPY public.qrtz_job_details (sched_name, job_name, job_group, description, job_class_name, is_durable, is_nonconcurrent, is_update_data, requests_recovery, job_data) FROM stdin;
RenrenScheduler	TASK_1	DEFAULT	\N	io.renren.modules.job.utils.ScheduleJob	0	0	0	0	\\xaced0005737200156f72672e71756172747a2e4a6f62446174614d61709fb083e8bfa9b0cb020000787200266f72672e71756172747a2e7574696c732e537472696e674b65794469727479466c61674d61708208e8c3fbc55d280200015a0013616c6c6f77735472616e7369656e74446174617872001d6f72672e71756172747a2e7574696c732e4469727479466c61674d617013e62ead28760ace0200025a000564697274794c00036d617074000f4c6a6176612f7574696c2f4d61703b787001737200116a6176612e7574696c2e486173684d61700507dac1c31660d103000246000a6c6f6164466163746f724900097468726573686f6c6478703f4000000000000c7708000000100000000174000d4a4f425f504152414d5f4b45597372002e696f2e72656e72656e2e6d6f64756c65732e6a6f622e656e746974792e5363686564756c654a6f62456e7469747900000000000000010200074c00086265616e4e616d657400124c6a6176612f6c616e672f537472696e673b4c000a63726561746554696d657400104c6a6176612f7574696c2f446174653b4c000e63726f6e45787072657373696f6e71007e00094c00056a6f6249647400104c6a6176612f6c616e672f4c6f6e673b4c0006706172616d7371007e00094c000672656d61726b71007e00094c00067374617475737400134c6a6176612f6c616e672f496e74656765723b7870740008746573745461736b7372000e6a6176612e7574696c2e44617465686a81014b597419030000787077080000016d7e5877707874000e3020302f3330202a202a202a203f7372000e6a6176612e6c616e672e4c6f6e673b8be490cc8f23df0200014a000576616c7565787200106a6176612e6c616e672e4e756d62657286ac951d0b94e08b0200007870000000000000000174000672656e72656e74000ce58f82e695b0e6b58be8af95737200116a6176612e6c616e672e496e746567657212e2a0a4f781873802000149000576616c75657871007e0013000000007800
\.


--
-- Data for Name: qrtz_locks; Type: TABLE DATA; Schema: public; Owner: faris
--

COPY public.qrtz_locks (sched_name, lock_name) FROM stdin;
RenrenScheduler	STATE_ACCESS
RenrenScheduler	TRIGGER_ACCESS
\.


--
-- Data for Name: qrtz_paused_trigger_grps; Type: TABLE DATA; Schema: public; Owner: faris
--

COPY public.qrtz_paused_trigger_grps (sched_name, trigger_group) FROM stdin;
\.


--
-- Data for Name: qrtz_scheduler_state; Type: TABLE DATA; Schema: public; Owner: faris
--

COPY public.qrtz_scheduler_state (sched_name, instance_name, last_checkin_time, checkin_interval) FROM stdin;
RenrenScheduler	398b46e373ca1583849178560	1583857581231	15000
\.


--
-- Data for Name: qrtz_simple_triggers; Type: TABLE DATA; Schema: public; Owner: faris
--

COPY public.qrtz_simple_triggers (sched_name, trigger_name, trigger_group, repeat_count, repeat_interval, times_triggered) FROM stdin;
\.


--
-- Data for Name: qrtz_simprop_triggers; Type: TABLE DATA; Schema: public; Owner: faris
--

COPY public.qrtz_simprop_triggers (sched_name, trigger_name, trigger_group, str_prop_1, str_prop_2, str_prop_3, int_prop_1, int_prop_2, long_prop_1, long_prop_2, dec_prop_1, dec_prop_2, bool_prop_1, bool_prop_2) FROM stdin;
\.


--
-- Data for Name: qrtz_triggers; Type: TABLE DATA; Schema: public; Owner: faris
--

COPY public.qrtz_triggers (sched_name, trigger_name, trigger_group, job_name, job_group, description, next_fire_time, prev_fire_time, priority, trigger_state, trigger_type, start_time, end_time, calendar_name, misfire_instr, job_data) FROM stdin;
RenrenScheduler	TASK_1	DEFAULT	TASK_1	DEFAULT	\N	1583857800000	1583856000000	5	WAITING	CRON	1569813024000	0	\N	2	\\xaced0005737200156f72672e71756172747a2e4a6f62446174614d61709fb083e8bfa9b0cb020000787200266f72672e71756172747a2e7574696c732e537472696e674b65794469727479466c61674d61708208e8c3fbc55d280200015a0013616c6c6f77735472616e7369656e74446174617872001d6f72672e71756172747a2e7574696c732e4469727479466c61674d617013e62ead28760ace0200025a000564697274794c00036d617074000f4c6a6176612f7574696c2f4d61703b787001737200116a6176612e7574696c2e486173684d61700507dac1c31660d103000246000a6c6f6164466163746f724900097468726573686f6c6478703f4000000000000c7708000000100000000174000d4a4f425f504152414d5f4b45597372002e696f2e72656e72656e2e6d6f64756c65732e6a6f622e656e746974792e5363686564756c654a6f62456e7469747900000000000000010200074c00086265616e4e616d657400124c6a6176612f6c616e672f537472696e673b4c000a63726561746554696d657400104c6a6176612f7574696c2f446174653b4c000e63726f6e45787072657373696f6e71007e00094c00056a6f6249647400104c6a6176612f6c616e672f4c6f6e673b4c0006706172616d7371007e00094c000672656d61726b71007e00094c00067374617475737400134c6a6176612f6c616e672f496e74656765723b7870740008746573745461736b7372000e6a6176612e7574696c2e44617465686a81014b597419030000787077080000016d7e5877707874000e3020302f3330202a202a202a203f7372000e6a6176612e6c616e672e4c6f6e673b8be490cc8f23df0200014a000576616c7565787200106a6176612e6c616e672e4e756d62657286ac951d0b94e08b0200007870000000000000000174000672656e72656e74000ce58f82e695b0e6b58be8af95737200116a6176612e6c616e672e496e746567657212e2a0a4f781873802000149000576616c75657871007e0013000000007800
\.


--
-- Data for Name: schedule_job; Type: TABLE DATA; Schema: public; Owner: faris
--

COPY public.schedule_job (job_id, bean_name, params, cron_expression, status, remark, create_time) FROM stdin;
1	testTask	renren	0 0/30 * * * ?	0	参数测试	2019-09-30 02:46:30+00
\.


--
-- Data for Name: schedule_job_log; Type: TABLE DATA; Schema: public; Owner: faris
--

COPY public.schedule_job_log (log_id, job_id, bean_name, params, status, error, times, create_time) FROM stdin;
1	1	testTask	renren	0	\N	0	2019-09-30 11:30:00+00
2	1	testTask	renren	0	\N	0	2019-09-30 12:00:00+00
3	1	testTask	renren	0	\N	1	2019-09-30 12:30:00+00
4	1	testTask	renren	0	\N	1	2019-09-30 13:00:00+00
5	1	testTask	renren	0	\N	1	2019-09-30 13:30:00+00
6	1	testTask	renren	0	\N	1	2019-09-30 14:00:00+00
7	1	testTask	renren	0	\N	1	2019-09-30 14:30:00+00
8	1	testTask	renren	0	\N	1	2019-09-30 15:00:00+00
9	1	testTask	renren	0	\N	0	2019-09-30 15:30:00+00
10	1	testTask	renren	0	\N	1	2019-09-30 16:00:00+00
11	1	testTask	renren	0	\N	0	2019-09-30 16:30:00+00
12	1	testTask	renren	0	\N	1	2019-09-30 17:00:00+00
13	1	testTask	renren	0	\N	1	2019-09-30 17:30:00+00
14	1	testTask	renren	0	\N	1	2019-09-30 18:00:00+00
15	1	testTask	renren	0	\N	1	2019-09-30 18:30:00+00
16	1	testTask	renren	0	\N	1	2019-09-30 19:00:00+00
17	1	testTask	renren	0	\N	1	2019-09-30 19:30:00+00
18	1	testTask	renren	0	\N	0	2019-09-30 20:00:00+00
19	1	testTask	renren	0	\N	0	2019-09-30 20:30:00+00
20	1	testTask	renren	0	\N	1	2019-09-30 21:00:00+00
21	1	testTask	renren	0	\N	1	2019-09-30 21:30:00+00
22	1	testTask	renren	0	\N	1	2019-09-30 22:00:00+00
23	1	testTask	renren	0	\N	1	2019-09-30 22:30:00+00
24	1	testTask	renren	0	\N	1	2019-09-30 23:00:00+00
25	1	testTask	renren	0	\N	0	2019-09-30 23:30:00+00
26	1	testTask	renren	0	\N	9	2019-10-01 00:00:00+00
27	1	testTask	renren	0	\N	1	2019-10-01 00:30:00+00
28	1	testTask	renren	0	\N	0	2019-10-01 01:00:00+00
29	1	testTask	renren	0	\N	0	2019-10-01 01:30:00+00
30	1	testTask	renren	0	\N	0	2019-10-01 02:00:00+00
31	1	testTask	renren	0	\N	1	2019-10-01 02:30:00+00
32	1	testTask	renren	0	\N	0	2019-10-01 03:00:00+00
33	1	testTask	renren	0	\N	0	2019-10-01 03:30:00+00
34	1	testTask	renren	0	\N	0	2019-10-01 04:00:00+00
35	1	testTask	renren	0	\N	0	2019-10-01 04:30:00+00
36	1	testTask	renren	0	\N	0	2019-10-01 05:00:00+00
37	1	testTask	renren	0	\N	0	2019-10-01 05:30:00+00
38	1	testTask	renren	0	\N	0	2019-10-01 06:00:00+00
39	1	testTask	renren	0	\N	1	2019-10-01 06:30:00+00
40	1	testTask	renren	0	\N	1	2019-10-01 07:00:00+00
41	1	testTask	renren	0	\N	1	2019-10-01 07:30:00+00
42	1	testTask	renren	0	\N	1	2019-10-01 08:00:00+00
43	1	testTask	renren	0	\N	0	2019-10-01 08:30:00+00
44	1	testTask	renren	0	\N	0	2019-10-01 09:00:00+00
45	1	testTask	renren	0	\N	0	2019-10-01 09:30:00+00
46	1	testTask	renren	0	\N	0	2019-10-01 10:00:00+00
47	1	testTask	renren	0	\N	1	2019-10-01 10:30:00+00
48	1	testTask	renren	0	\N	0	2019-10-01 11:00:00+00
49	1	testTask	renren	0	\N	0	2019-10-01 11:30:00+00
50	1	testTask	renren	0	\N	0	2019-10-01 12:00:00+00
51	1	testTask	renren	0	\N	0	2019-10-01 12:30:00+00
52	1	testTask	renren	0	\N	0	2019-10-01 13:00:00+00
53	1	testTask	renren	0	\N	0	2019-10-01 13:30:00+00
54	1	testTask	renren	0	\N	1	2019-10-01 14:00:00+00
55	1	testTask	renren	0	\N	0	2019-10-01 14:30:00+00
56	1	testTask	renren	0	\N	1	2019-10-01 19:00:00+00
57	1	testTask	renren	0	\N	0	2019-10-01 19:30:00+00
58	1	testTask	renren	0	\N	0	2019-10-01 20:00:00+00
59	1	testTask	renren	0	\N	0	2019-10-01 20:30:00+00
60	1	testTask	renren	0	\N	0	2019-10-01 21:00:00+00
61	1	testTask	renren	0	\N	0	2019-10-01 21:30:00+00
62	1	testTask	renren	0	\N	1	2019-10-01 22:00:00+00
63	1	testTask	renren	0	\N	0	2019-10-01 22:30:00+00
64	1	testTask	renren	0	\N	1	2019-10-01 23:00:00+00
65	1	testTask	renren	0	\N	1	2019-10-01 23:30:00+00
66	1	testTask	renren	0	\N	5	2019-10-02 00:00:00+00
67	1	testTask	renren	0	\N	1	2019-10-02 00:30:00+00
68	1	testTask	renren	0	\N	0	2019-10-02 10:30:00+00
69	1	testTask	renren	0	\N	1	2019-10-02 11:00:00+00
70	1	testTask	renren	0	\N	1	2019-10-02 11:30:00+00
71	1	testTask	renren	0	\N	1	2019-10-02 12:00:00+00
72	1	testTask	renren	0	\N	1	2019-10-02 12:30:00+00
73	1	testTask	renren	0	\N	1	2019-10-02 13:00:00+00
74	1	testTask	renren	0	\N	1	2019-10-28 22:00:00+00
75	1	testTask	renren	0	\N	1	2019-10-28 22:30:00+00
76	1	testTask	renren	0	\N	1	2019-10-28 23:30:00+00
77	1	testTask	renren	0	\N	5	2019-10-29 00:00:00+00
78	1	testTask	renren	0	\N	2	2019-10-29 00:30:00+00
79	1	testTask	renren	0	\N	1	2019-10-29 01:00:00+00
80	1	testTask	renren	0	\N	1	2019-10-29 01:30:00+00
81	1	testTask	renren	0	\N	1	2019-10-29 02:00:00+00
82	1	testTask	renren	0	\N	1	2019-10-29 02:30:00+00
83	1	testTask	renren	0	\N	0	2019-10-29 03:00:00+00
84	1	testTask	renren	0	\N	1	2019-10-29 03:30:00+00
85	1	testTask	renren	0	\N	0	2019-10-29 04:00:00+00
86	1	testTask	renren	0	\N	1	2019-10-29 04:30:00+00
87	1	testTask	renren	0	\N	1	2019-10-29 05:00:00+00
88	1	testTask	renren	0	\N	1	2019-10-29 05:30:00+00
89	1	testTask	renren	0	\N	1	2019-10-29 06:00:00+00
90	1	testTask	renren	0	\N	1	2019-10-29 06:30:00+00
91	1	testTask	renren	0	\N	1	2019-10-29 07:00:00+00
92	1	testTask	renren	0	\N	1	2019-10-29 07:30:00+00
93	1	testTask	renren	0	\N	0	2019-10-29 08:00:00+00
94	1	testTask	renren	0	\N	1	2019-10-29 08:30:00+00
95	1	testTask	renren	0	\N	4	2019-10-29 09:00:00+00
96	1	testTask	renren	0	\N	1	2019-10-29 09:30:00+00
97	1	testTask	renren	0	\N	1	2019-10-29 10:00:00+00
98	1	testTask	renren	0	\N	1	2019-10-29 10:30:00+00
99	1	testTask	renren	0	\N	1	2019-10-29 11:00:00+00
100	1	testTask	renren	0	\N	0	2019-10-29 11:30:00+00
101	1	testTask	renren	0	\N	1	2019-10-29 12:00:00+00
102	1	testTask	renren	0	\N	1	2019-10-29 12:30:00+00
103	1	testTask	renren	0	\N	1	2019-10-29 13:00:00+00
104	1	testTask	renren	0	\N	1	2019-10-29 13:30:00+00
105	1	testTask	renren	0	\N	1	2019-10-29 14:00:00+00
106	1	testTask	renren	0	\N	1	2019-10-29 14:30:00+00
107	1	testTask	renren	0	\N	1	2019-10-29 15:00:00+00
108	1	testTask	renren	0	\N	1	2019-10-29 15:30:00+00
109	1	testTask	renren	0	\N	1	2019-10-29 16:00:00+00
110	1	testTask	renren	0	\N	1	2019-10-29 16:30:00+00
111	1	testTask	renren	0	\N	1	2019-10-29 17:00:00+00
112	1	testTask	renren	0	\N	1	2019-10-29 17:30:00+00
113	1	testTask	renren	0	\N	1	2019-10-29 18:00:00+00
114	1	testTask	renren	0	\N	1	2019-10-29 18:30:00+00
115	1	testTask	renren	0	\N	1	2019-10-29 19:00:00+00
116	1	testTask	renren	0	\N	1	2019-10-29 19:30:00+00
117	1	testTask	renren	0	\N	1	2019-10-29 20:00:00+00
118	1	testTask	renren	0	\N	1	2019-10-29 20:30:00+00
119	1	testTask	renren	0	\N	1	2019-10-29 21:30:03+00
120	1	testTask	renren	0	\N	0	2019-10-29 22:00:00+00
121	1	testTask	renren	0	\N	1	2019-10-29 22:30:00+00
122	1	testTask	renren	0	\N	1	2019-10-29 23:00:00+00
123	1	testTask	renren	0	\N	1	2019-10-29 23:30:00+00
124	1	testTask	renren	0	\N	14	2019-10-30 00:00:00+00
125	1	testTask	renren	0	\N	1	2019-10-30 00:30:00+00
126	1	testTask	renren	0	\N	0	2019-10-30 01:00:00+00
127	1	testTask	renren	0	\N	1	2019-10-30 01:30:00+00
128	1	testTask	renren	0	\N	1	2019-10-30 02:00:00+00
129	1	testTask	renren	0	\N	0	2019-10-30 02:30:00+00
130	1	testTask	renren	0	\N	1	2019-10-30 03:00:00+00
131	1	testTask	renren	0	\N	1	2019-10-30 03:30:00+00
132	1	testTask	renren	0	\N	1	2019-10-30 04:00:00+00
133	1	testTask	renren	0	\N	0	2019-10-30 04:30:00+00
134	1	testTask	renren	0	\N	1	2019-10-30 05:00:00+00
135	1	testTask	renren	0	\N	1	2019-10-30 05:30:00+00
136	1	testTask	renren	0	\N	1	2019-10-30 06:00:00+00
137	1	testTask	renren	0	\N	1	2019-10-30 06:30:00+00
138	1	testTask	renren	0	\N	0	2019-10-30 07:00:00+00
139	1	testTask	renren	0	\N	1	2019-10-30 07:30:00+00
140	1	testTask	renren	0	\N	1	2019-10-30 08:00:00+00
141	1	testTask	renren	0	\N	0	2019-10-30 08:30:00+00
142	1	testTask	renren	0	\N	1	2019-10-30 09:00:00+00
143	1	testTask	renren	0	\N	0	2019-10-30 09:30:00+00
144	1	testTask	renren	0	\N	0	2019-10-30 10:00:00+00
145	1	testTask	renren	0	\N	1	2019-10-30 10:30:00+00
146	1	testTask	renren	0	\N	0	2019-10-30 11:00:00+00
147	1	testTask	renren	0	\N	2	2019-10-30 11:30:00+00
148	1	testTask	renren	0	\N	1	2019-10-30 12:00:00+00
149	1	testTask	renren	0	\N	1	2019-10-30 12:30:00+00
150	1	testTask	renren	0	\N	0	2019-10-30 13:00:00+00
151	1	testTask	renren	0	\N	0	2019-10-30 13:30:00+00
152	1	testTask	renren	0	\N	1	2019-10-30 14:00:00+00
153	1	testTask	renren	0	\N	1	2019-10-30 14:30:00+00
154	1	testTask	renren	0	\N	1	2019-10-30 15:00:00+00
155	1	testTask	renren	0	\N	1	2019-10-30 15:30:00+00
156	1	testTask	renren	0	\N	2	2019-10-30 16:00:00+00
157	1	testTask	renren	0	\N	1	2019-10-30 16:30:00+00
158	1	testTask	renren	0	\N	6	2019-10-30 17:00:00+00
159	1	testTask	renren	0	\N	1	2019-10-30 17:30:00+00
160	1	testTask	renren	0	\N	0	2019-10-30 18:00:00+00
161	1	testTask	renren	0	\N	0	2019-10-30 18:30:00+00
162	1	testTask	renren	0	\N	1	2019-10-30 19:00:00+00
163	1	testTask	renren	0	\N	1	2019-10-30 19:30:00+00
164	1	testTask	renren	0	\N	1	2019-10-30 20:00:00+00
165	1	testTask	renren	0	\N	1	2019-10-30 20:30:00+00
166	1	testTask	renren	0	\N	1	2019-10-30 21:00:00+00
167	1	testTask	renren	0	\N	0	2019-10-30 21:30:00+00
168	1	testTask	renren	0	\N	0	2019-10-30 22:00:00+00
169	1	testTask	renren	0	\N	1	2019-10-30 22:30:00+00
170	1	testTask	renren	0	\N	0	2019-10-30 23:00:00+00
171	1	testTask	renren	0	\N	0	2019-10-30 23:30:00+00
172	1	testTask	renren	0	\N	5	2019-10-31 00:00:00+00
173	1	testTask	renren	0	\N	1	2019-10-31 00:30:00+00
174	1	testTask	renren	0	\N	0	2019-10-31 01:00:00+00
175	1	testTask	renren	0	\N	0	2019-10-31 01:30:00+00
176	1	testTask	renren	0	\N	1	2019-10-31 02:00:00+00
177	1	testTask	renren	0	\N	1	2019-10-31 02:30:00+00
178	1	testTask	renren	0	\N	1	2019-10-31 03:00:00+00
179	1	testTask	renren	0	\N	1	2019-10-31 03:30:00+00
180	1	testTask	renren	0	\N	0	2019-10-31 04:00:00+00
181	1	testTask	renren	0	\N	0	2019-10-31 04:30:00+00
182	1	testTask	renren	0	\N	1	2019-10-31 05:00:00+00
183	1	testTask	renren	0	\N	1	2019-10-31 05:30:00+00
184	1	testTask	renren	0	\N	1	2019-10-31 06:00:00+00
185	1	testTask	renren	0	\N	1	2019-10-31 06:30:00+00
186	1	testTask	renren	0	\N	0	2019-10-31 07:00:00+00
187	1	testTask	renren	0	\N	0	2019-10-31 07:30:00+00
188	1	testTask	renren	0	\N	1	2019-10-31 08:00:00+00
189	1	testTask	renren	0	\N	1	2019-10-31 08:30:00+00
190	1	testTask	renren	0	\N	0	2019-10-31 09:00:00+00
191	1	testTask	renren	0	\N	1	2019-10-31 09:30:00+00
192	1	testTask	renren	0	\N	0	2019-10-31 10:00:00+00
193	1	testTask	renren	0	\N	0	2019-10-31 10:30:00+00
194	1	testTask	renren	0	\N	1	2019-10-31 11:00:00+00
195	1	testTask	renren	0	\N	0	2019-10-31 11:30:00+00
196	1	testTask	renren	0	\N	1	2019-10-31 12:00:00+00
197	1	testTask	renren	0	\N	0	2019-10-31 12:30:00+00
198	1	testTask	renren	0	\N	1	2019-10-31 13:00:00+00
199	1	testTask	renren	0	\N	2	2019-10-31 13:30:00+00
200	1	testTask	renren	0	\N	1	2019-10-31 14:00:00+00
201	1	testTask	renren	0	\N	1	2019-10-31 14:30:00+00
202	1	testTask	renren	0	\N	1	2019-10-31 15:00:00+00
203	1	testTask	renren	0	\N	0	2019-10-31 15:30:00+00
204	1	testTask	renren	0	\N	1	2019-10-31 16:00:00+00
205	1	testTask	renren	0	\N	0	2019-10-31 16:30:00+00
206	1	testTask	renren	0	\N	1	2019-10-31 17:00:00+00
207	1	testTask	renren	0	\N	1	2019-10-31 17:30:00+00
208	1	testTask	renren	0	\N	0	2019-10-31 18:00:00+00
209	1	testTask	renren	0	\N	1	2019-10-31 18:30:00+00
210	1	testTask	renren	0	\N	1	2019-10-31 19:00:00+00
211	1	testTask	renren	0	\N	1	2019-10-31 19:30:00+00
212	1	testTask	renren	0	\N	0	2019-10-31 20:00:00+00
213	1	testTask	renren	0	\N	1	2019-10-31 20:30:00+00
214	1	testTask	renren	0	\N	1	2019-10-31 21:00:00+00
215	1	testTask	renren	0	\N	0	2019-10-31 21:30:00+00
216	1	testTask	renren	0	\N	0	2019-10-31 22:00:00+00
217	1	testTask	renren	0	\N	0	2019-10-31 22:30:00+00
218	1	testTask	renren	0	\N	1	2019-10-31 23:00:00+00
219	1	testTask	renren	0	\N	1	2019-10-31 23:30:00+00
220	1	testTask	renren	0	\N	6	2019-11-01 00:00:00+00
221	1	testTask	renren	0	\N	2	2019-11-01 00:30:00+00
222	1	testTask	renren	0	\N	1	2019-11-01 01:00:00+00
223	1	testTask	renren	0	\N	1	2019-11-01 01:30:00+00
224	1	testTask	renren	0	\N	0	2019-11-01 02:00:00+00
225	1	testTask	renren	0	\N	1	2019-11-01 02:30:00+00
226	1	testTask	renren	0	\N	0	2019-11-01 03:00:00+00
227	1	testTask	renren	0	\N	1	2019-11-01 03:30:00+00
228	1	testTask	renren	0	\N	1	2019-11-01 04:00:00+00
229	1	testTask	renren	0	\N	1	2019-11-01 04:30:00+00
230	1	testTask	renren	0	\N	0	2019-11-01 05:00:00+00
231	1	testTask	renren	0	\N	0	2019-11-01 05:30:00+00
232	1	testTask	renren	0	\N	1	2019-11-01 06:00:00+00
233	1	testTask	renren	0	\N	1	2019-11-01 06:30:00+00
234	1	testTask	renren	0	\N	0	2019-11-01 07:00:00+00
235	1	testTask	renren	0	\N	0	2019-11-01 07:30:00+00
236	1	testTask	renren	0	\N	0	2019-11-01 08:00:00+00
237	1	testTask	renren	0	\N	0	2019-11-01 08:30:00+00
238	1	testTask	renren	0	\N	1	2019-11-01 09:00:00+00
239	1	testTask	renren	0	\N	1	2019-11-01 09:30:00+00
240	1	testTask	renren	0	\N	1	2019-11-01 10:00:00+00
241	1	testTask	renren	0	\N	1	2019-11-01 10:30:00+00
242	1	testTask	renren	0	\N	0	2019-11-01 11:00:00+00
243	1	testTask	renren	0	\N	1	2019-11-01 11:30:00+00
244	1	testTask	renren	0	\N	1	2019-11-01 12:00:00+00
245	1	testTask	renren	0	\N	0	2019-11-01 12:30:00+00
246	1	testTask	renren	0	\N	1	2019-11-01 13:00:00+00
247	1	testTask	renren	0	\N	0	2019-11-01 13:30:00+00
248	1	testTask	renren	0	\N	1	2019-11-01 14:00:00+00
249	1	testTask	renren	0	\N	0	2019-11-01 14:30:00+00
250	1	testTask	renren	0	\N	1	2019-11-01 15:00:00+00
251	1	testTask	renren	0	\N	1	2019-11-01 15:30:00+00
252	1	testTask	renren	0	\N	0	2019-11-01 16:00:00+00
253	1	testTask	renren	0	\N	1	2019-11-01 16:30:00+00
254	1	testTask	renren	0	\N	1	2019-11-01 17:00:00+00
255	1	testTask	renren	0	\N	1	2019-11-01 17:30:00+00
256	1	testTask	renren	0	\N	1	2019-11-01 18:00:00+00
257	1	testTask	renren	0	\N	1	2019-11-01 18:30:00+00
258	1	testTask	renren	0	\N	0	2019-11-01 19:00:00+00
259	1	testTask	renren	0	\N	1	2019-11-01 19:30:00+00
260	1	testTask	renren	0	\N	1	2019-11-01 20:00:00+00
261	1	testTask	renren	0	\N	0	2019-11-01 20:30:00+00
262	1	testTask	renren	0	\N	1	2019-11-01 21:00:00+00
263	1	testTask	renren	0	\N	0	2019-11-01 21:30:00+00
264	1	testTask	renren	0	\N	0	2019-11-01 22:30:00+00
265	1	testTask	renren	0	\N	1	2019-11-01 23:00:00+00
266	1	testTask	renren	0	\N	1	2019-11-01 23:30:00+00
267	1	testTask	renren	0	\N	12	2019-11-02 00:00:00+00
268	1	testTask	renren	0	\N	4	2019-11-02 00:30:00+00
269	1	testTask	renren	0	\N	1	2019-11-02 10:30:00+00
270	1	testTask	renren	0	\N	0	2019-11-02 11:00:00+00
271	1	testTask	renren	0	\N	0	2019-11-02 11:30:00+00
272	1	testTask	renren	0	\N	1	2019-11-02 12:00:00+00
273	1	testTask	renren	0	\N	1	2019-11-02 12:30:00+00
274	1	testTask	renren	0	\N	0	2019-11-02 13:00:00+00
275	1	testTask	renren	0	\N	0	2019-11-02 13:30:00+00
276	1	testTask	renren	0	\N	1	2019-11-02 14:00:00+00
277	1	testTask	renren	0	\N	1	2019-11-02 14:30:00+00
278	1	testTask	renren	0	\N	1	2019-11-02 15:00:00+00
279	1	testTask	renren	0	\N	1	2019-11-02 15:30:00+00
280	1	testTask	renren	0	\N	1	2019-11-02 16:00:00+00
281	1	testTask	renren	0	\N	1	2019-11-02 16:30:00+00
282	1	testTask	renren	0	\N	1	2019-11-02 17:00:00+00
283	1	testTask	renren	0	\N	1	2019-11-02 17:30:00+00
284	1	testTask	renren	0	\N	1	2019-11-02 18:00:00+00
285	1	testTask	renren	0	\N	1	2019-11-02 18:30:00+00
286	1	testTask	renren	0	\N	1	2019-11-02 19:00:00+00
287	1	testTask	renren	0	\N	1	2019-11-02 19:30:00+00
288	1	testTask	renren	0	\N	0	2019-11-02 20:00:00+00
289	1	testTask	renren	0	\N	0	2019-11-02 20:30:00+00
290	1	testTask	renren	0	\N	0	2019-11-04 10:00:00+00
291	1	testTask	renren	0	\N	0	2019-11-04 10:30:00+00
292	1	testTask	renren	0	\N	1	2019-11-04 11:00:00+00
293	1	testTask	renren	0	\N	0	2019-11-04 11:30:00+00
294	1	testTask	renren	0	\N	0	2019-11-04 12:00:00+00
295	1	testTask	renren	0	\N	1	2019-11-04 12:30:00+00
296	1	testTask	renren	0	\N	1	2019-11-04 13:00:00+00
297	1	testTask	renren	0	\N	1	2019-11-04 13:30:00+00
298	1	testTask	renren	0	\N	1	2019-11-04 14:00:00+00
299	1	testTask	renren	0	\N	0	2019-11-04 14:30:00+00
300	1	testTask	renren	0	\N	1	2019-11-04 15:00:00+00
301	1	testTask	renren	0	\N	0	2019-11-04 15:30:00+00
302	1	testTask	renren	0	\N	1	2019-11-04 16:00:00+00
303	1	testTask	renren	0	\N	1	2019-11-04 16:30:00+00
304	1	testTask	renren	0	\N	1	2019-11-04 17:00:00+00
305	1	testTask	renren	0	\N	1	2019-11-04 17:30:00+00
306	1	testTask	renren	0	\N	0	2019-11-04 18:00:00+00
307	1	testTask	renren	0	\N	0	2019-11-04 18:30:00+00
308	1	testTask	renren	0	\N	0	2019-11-04 19:00:00+00
309	1	testTask	renren	0	\N	1	2019-11-04 19:30:00+00
310	1	testTask	renren	0	\N	1	2019-11-04 20:00:00+00
311	1	testTask	renren	0	\N	1	2019-11-04 20:30:00+00
312	1	testTask	renren	0	\N	1	2019-11-04 21:00:00+00
313	1	testTask	renren	0	\N	1	2019-11-04 21:30:00+00
314	1	testTask	renren	0	\N	1	2019-11-04 22:00:00+00
315	1	testTask	renren	0	\N	1	2019-11-04 22:30:00+00
316	1	testTask	renren	0	\N	1	2019-11-04 23:00:00+00
317	1	testTask	renren	0	\N	1	2019-11-04 23:30:00+00
318	1	testTask	renren	0	\N	7	2019-11-05 00:00:00+00
319	1	testTask	renren	0	\N	1	2019-11-05 00:30:00+00
320	1	testTask	renren	0	\N	1	2019-11-05 01:00:00+00
321	1	testTask	renren	0	\N	0	2019-11-05 01:30:00+00
322	1	testTask	renren	0	\N	0	2019-11-05 02:00:00+00
323	1	testTask	renren	0	\N	0	2019-11-05 02:30:00+00
324	1	testTask	renren	0	\N	1	2019-11-05 03:00:00+00
325	1	testTask	renren	0	\N	1	2019-11-05 03:30:00+00
326	1	testTask	renren	0	\N	0	2019-11-05 04:00:00+00
327	1	testTask	renren	0	\N	0	2019-11-05 04:30:00+00
328	1	testTask	renren	0	\N	0	2019-11-05 05:00:00+00
329	1	testTask	renren	0	\N	0	2019-11-05 05:30:00+00
330	1	testTask	renren	0	\N	1	2019-11-05 06:00:00+00
331	1	testTask	renren	0	\N	1	2019-11-05 06:30:00+00
332	1	testTask	renren	0	\N	1	2019-11-05 07:00:00+00
333	1	testTask	renren	0	\N	1	2019-11-05 07:30:00+00
334	1	testTask	renren	0	\N	1	2019-11-05 08:00:00+00
335	1	testTask	renren	0	\N	0	2019-11-05 08:30:00+00
336	1	testTask	renren	0	\N	0	2019-11-05 09:00:00+00
337	1	testTask	renren	0	\N	0	2019-11-05 09:30:00+00
338	1	testTask	renren	0	\N	1	2019-11-05 10:00:00+00
339	1	testTask	renren	0	\N	1	2019-11-05 10:30:00+00
340	1	testTask	renren	0	\N	1	2019-11-05 11:00:00+00
341	1	testTask	renren	0	\N	1	2019-11-05 11:30:00+00
342	1	testTask	renren	0	\N	1	2019-11-05 12:00:00+00
343	1	testTask	renren	0	\N	0	2019-11-05 12:30:00+00
344	1	testTask	renren	0	\N	0	2019-11-05 13:00:00+00
345	1	testTask	renren	0	\N	1	2019-11-05 13:30:00+00
346	1	testTask	renren	0	\N	1	2019-11-05 14:00:00+00
347	1	testTask	renren	0	\N	0	2019-11-05 14:30:00+00
348	1	testTask	renren	0	\N	1	2019-11-05 15:00:00+00
349	1	testTask	renren	0	\N	1	2019-11-05 15:30:00+00
350	1	testTask	renren	0	\N	1	2019-11-05 16:00:00+00
351	1	testTask	renren	0	\N	1	2019-11-05 16:30:00+00
352	1	testTask	renren	0	\N	1	2019-11-05 17:00:00+00
353	1	testTask	renren	0	\N	1	2019-11-05 17:30:02+00
354	1	testTask	renren	0	\N	0	2019-11-05 18:00:00+00
355	1	testTask	renren	0	\N	1	2019-11-05 18:30:00+00
356	1	testTask	renren	0	\N	0	2019-11-05 19:00:00+00
357	1	testTask	renren	0	\N	0	2019-11-05 19:30:00+00
358	1	testTask	renren	0	\N	0	2019-11-05 20:00:00+00
359	1	testTask	renren	0	\N	0	2019-11-05 20:30:00+00
360	1	testTask	renren	0	\N	1	2019-11-05 21:00:00+00
361	1	testTask	renren	0	\N	1	2019-11-05 21:30:00+00
362	1	testTask	renren	0	\N	1	2019-11-05 22:00:00+00
363	1	testTask	renren	0	\N	0	2019-11-05 22:30:00+00
364	1	testTask	renren	0	\N	1	2019-11-05 23:00:00+00
365	1	testTask	renren	0	\N	1	2019-11-05 23:30:00+00
366	1	testTask	renren	0	\N	4	2019-11-06 00:00:00+00
367	1	testTask	renren	0	\N	1	2019-11-06 00:30:00+00
368	1	testTask	renren	0	\N	1	2019-11-06 01:00:00+00
369	1	testTask	renren	0	\N	1	2019-11-06 01:30:00+00
370	1	testTask	renren	0	\N	1	2019-11-06 02:00:00+00
371	1	testTask	renren	0	\N	1	2019-11-06 02:30:00+00
372	1	testTask	renren	0	\N	0	2019-11-06 03:00:00+00
373	1	testTask	renren	0	\N	0	2019-11-06 03:30:00+00
374	1	testTask	renren	0	\N	0	2019-11-06 04:00:00+00
375	1	testTask	renren	0	\N	0	2019-11-06 04:30:00+00
376	1	testTask	renren	0	\N	0	2019-11-06 05:00:00+00
377	1	testTask	renren	0	\N	1	2019-11-06 05:30:00+00
378	1	testTask	renren	0	\N	0	2019-11-06 06:00:00+00
379	1	testTask	renren	0	\N	0	2019-11-06 06:30:00+00
380	1	testTask	renren	0	\N	1	2019-11-06 07:00:00+00
381	1	testTask	renren	0	\N	0	2019-11-06 07:30:00+00
382	1	testTask	renren	0	\N	0	2019-11-06 08:00:00+00
383	1	testTask	renren	0	\N	0	2019-11-06 08:30:00+00
384	1	testTask	renren	0	\N	1	2019-11-06 09:00:00+00
385	1	testTask	renren	0	\N	0	2019-11-06 09:30:00+00
386	1	testTask	renren	0	\N	0	2019-11-06 10:00:00+00
387	1	testTask	renren	0	\N	0	2019-11-06 10:30:00+00
388	1	testTask	renren	0	\N	1	2019-11-06 11:00:00+00
389	1	testTask	renren	0	\N	0	2019-11-06 11:30:00+00
390	1	testTask	renren	0	\N	1	2019-11-06 12:00:00+00
391	1	testTask	renren	0	\N	0	2019-11-06 12:30:00+00
392	1	testTask	renren	0	\N	1	2019-11-06 13:00:00+00
393	1	testTask	renren	0	\N	0	2019-11-06 13:30:00+00
394	1	testTask	renren	0	\N	1	2019-11-06 14:00:00+00
395	1	testTask	renren	0	\N	1	2019-11-06 14:30:00+00
396	1	testTask	renren	0	\N	1	2019-11-06 15:00:00+00
397	1	testTask	renren	0	\N	1	2019-11-06 15:30:00+00
398	1	testTask	renren	0	\N	1	2019-11-06 16:00:00+00
399	1	testTask	renren	0	\N	1	2019-11-06 16:30:00+00
400	1	testTask	renren	0	\N	0	2019-11-06 17:00:00+00
401	1	testTask	renren	0	\N	1	2019-11-06 17:30:00+00
402	1	testTask	renren	0	\N	1	2019-11-06 18:00:00+00
403	1	testTask	renren	0	\N	1	2019-11-06 18:30:00+00
404	1	testTask	renren	0	\N	1	2019-11-06 19:00:00+00
405	1	testTask	renren	0	\N	1	2019-11-06 19:30:00+00
406	1	testTask	renren	0	\N	1	2019-11-06 20:00:00+00
407	1	testTask	renren	0	\N	1	2019-11-06 20:30:00+00
408	1	testTask	renren	0	\N	1	2019-11-06 21:00:00+00
409	1	testTask	renren	0	\N	1	2019-11-06 21:30:00+00
410	1	testTask	renren	0	\N	1	2019-11-06 22:00:00+00
411	1	testTask	renren	0	\N	1	2019-11-06 22:30:00+00
412	1	testTask	renren	0	\N	0	2019-11-06 23:00:00+00
413	1	testTask	renren	0	\N	1	2019-11-06 23:30:00+00
414	1	testTask	renren	0	\N	5	2019-11-07 00:00:00+00
415	1	testTask	renren	0	\N	2	2019-11-07 00:30:00+00
416	1	testTask	renren	0	\N	0	2019-11-07 01:00:00+00
417	1	testTask	renren	0	\N	1	2019-11-07 01:30:00+00
418	1	testTask	renren	0	\N	1	2019-11-07 02:00:00+00
419	1	testTask	renren	0	\N	0	2019-11-07 02:30:00+00
420	1	testTask	renren	0	\N	1	2019-11-07 03:00:00+00
421	1	testTask	renren	0	\N	1	2019-11-07 03:30:00+00
422	1	testTask	renren	0	\N	1	2019-11-07 04:00:00+00
423	1	testTask	renren	0	\N	1	2019-11-07 04:30:00+00
424	1	testTask	renren	0	\N	0	2019-11-07 05:00:00+00
425	1	testTask	renren	0	\N	1	2019-11-07 05:30:00+00
426	1	testTask	renren	0	\N	1	2019-11-07 06:00:00+00
427	1	testTask	renren	0	\N	0	2019-11-07 06:30:00+00
428	1	testTask	renren	0	\N	1	2019-11-07 07:00:00+00
429	1	testTask	renren	0	\N	1	2019-11-07 07:30:00+00
430	1	testTask	renren	0	\N	0	2019-11-07 08:00:00+00
431	1	testTask	renren	0	\N	1	2019-11-07 08:30:00+00
432	1	testTask	renren	0	\N	1	2019-11-07 09:00:00+00
433	1	testTask	renren	0	\N	0	2019-11-07 09:30:00+00
434	1	testTask	renren	0	\N	1	2019-11-07 10:00:00+00
435	1	testTask	renren	0	\N	0	2019-11-07 10:30:00+00
436	1	testTask	renren	0	\N	1	2019-11-07 11:00:00+00
437	1	testTask	renren	0	\N	0	2019-11-07 11:30:00+00
438	1	testTask	renren	0	\N	1	2019-11-07 12:00:00+00
439	1	testTask	renren	0	\N	1	2019-11-07 12:30:00+00
440	1	testTask	renren	0	\N	1	2019-11-07 13:00:00+00
441	1	testTask	renren	0	\N	1	2019-11-07 13:30:00+00
442	1	testTask	renren	0	\N	0	2019-11-07 14:00:00+00
443	1	testTask	renren	0	\N	1	2019-11-07 14:30:00+00
444	1	testTask	renren	0	\N	1	2019-11-07 15:00:00+00
445	1	testTask	renren	0	\N	1	2019-11-07 15:30:00+00
446	1	testTask	renren	0	\N	1	2019-11-07 16:00:00+00
447	1	testTask	renren	0	\N	0	2019-11-07 16:30:00+00
448	1	testTask	renren	0	\N	1	2019-11-07 17:00:00+00
449	1	testTask	renren	0	\N	1	2019-11-07 17:30:00+00
450	1	testTask	renren	0	\N	1	2019-11-07 18:00:00+00
451	1	testTask	renren	0	\N	1	2019-11-07 18:30:00+00
452	1	testTask	renren	0	\N	1	2019-11-07 19:00:00+00
453	1	testTask	renren	0	\N	1	2019-11-07 19:30:00+00
454	1	testTask	renren	0	\N	1	2019-11-07 20:00:00+00
455	1	testTask	renren	0	\N	1	2019-11-07 20:30:00+00
456	1	testTask	renren	0	\N	0	2019-11-07 21:00:00+00
457	1	testTask	renren	0	\N	1	2019-11-07 21:30:00+00
458	1	testTask	renren	0	\N	1	2019-11-07 22:00:00+00
459	1	testTask	renren	0	\N	1	2019-11-07 22:30:00+00
460	1	testTask	renren	0	\N	1	2019-11-07 23:00:00+00
461	1	testTask	renren	0	\N	0	2019-11-07 23:30:00+00
462	1	testTask	renren	0	\N	7	2019-11-08 11:00:00+00
463	1	testTask	renren	0	\N	1	2019-11-08 11:30:00+00
464	1	testTask	renren	0	\N	0	2019-11-08 12:00:00+00
465	1	testTask	renren	0	\N	1	2019-11-08 12:30:00+00
466	1	testTask	renren	0	\N	0	2019-11-08 13:00:00+00
467	1	testTask	renren	0	\N	0	2019-11-08 13:30:00+00
468	1	testTask	renren	0	\N	0	2019-11-08 14:00:00+00
469	1	testTask	renren	0	\N	1	2019-11-08 14:30:00+00
470	1	testTask	renren	0	\N	1	2019-11-08 15:00:00+00
471	1	testTask	renren	0	\N	0	2019-11-08 15:30:00+00
472	1	testTask	renren	0	\N	1	2019-11-11 09:30:00+00
473	1	testTask	renren	0	\N	1	2019-11-11 10:00:00+00
474	1	testTask	renren	0	\N	1	2019-11-11 10:30:00+00
475	1	testTask	renren	0	\N	0	2019-11-11 11:00:00+00
476	1	testTask	renren	0	\N	0	2019-11-11 11:30:00+00
477	1	testTask	renren	0	\N	1	2019-11-11 12:00:00+00
478	1	testTask	renren	0	\N	0	2019-11-11 12:30:00+00
479	1	testTask	renren	0	\N	0	2019-11-11 13:00:00+00
480	1	testTask	renren	0	\N	1	2019-11-11 13:30:00+00
481	1	testTask	renren	0	\N	1	2019-11-11 14:00:00+00
482	1	testTask	renren	0	\N	1	2019-11-11 14:30:00+00
483	1	testTask	renren	0	\N	1	2019-11-11 15:00:00+00
484	1	testTask	renren	0	\N	0	2019-11-11 15:30:00+00
485	1	testTask	renren	0	\N	1	2019-11-11 16:00:00+00
486	1	testTask	renren	0	\N	1	2019-11-11 16:30:00+00
487	1	testTask	renren	0	\N	1	2019-11-11 17:00:00+00
488	1	testTask	renren	0	\N	1	2019-11-11 17:30:00+00
489	1	testTask	renren	0	\N	1	2019-11-11 18:00:00+00
490	1	testTask	renren	0	\N	1	2019-11-11 18:30:00+00
491	1	testTask	renren	0	\N	1	2019-11-11 20:00:00+00
492	1	testTask	renren	0	\N	1	2019-11-11 20:30:00+00
493	1	testTask	renren	0	\N	0	2019-11-11 21:00:00+00
494	1	testTask	renren	0	\N	1	2019-11-11 21:30:00+00
495	1	testTask	renren	0	\N	0	2019-11-11 22:00:00+00
496	1	testTask	renren	0	\N	1	2019-11-11 22:30:00+00
497	1	testTask	renren	0	\N	0	2019-11-11 23:00:00+00
498	1	testTask	renren	0	\N	1	2019-11-11 23:30:00+00
499	1	testTask	renren	0	\N	11	2019-11-12 00:00:00+00
500	1	testTask	renren	0	\N	2	2019-11-12 09:30:00+00
501	1	testTask	renren	0	\N	1	2019-11-12 10:00:00+00
502	1	testTask	renren	0	\N	1	2019-11-12 10:30:00+00
503	1	testTask	renren	0	\N	1	2019-11-12 11:00:00+00
504	1	testTask	renren	0	\N	0	2019-11-12 11:30:00+00
505	1	testTask	renren	0	\N	1	2019-11-12 12:00:00+00
506	1	testTask	renren	0	\N	1	2019-11-12 12:30:00+00
507	1	testTask	renren	0	\N	1	2019-11-12 13:00:00+00
508	1	testTask	renren	0	\N	0	2019-11-12 13:30:00+00
509	1	testTask	renren	0	\N	0	2019-11-12 14:00:00+00
510	1	testTask	renren	0	\N	1	2019-11-12 14:30:00+00
511	1	testTask	renren	0	\N	1	2019-11-12 15:00:00+00
512	1	testTask	renren	0	\N	1	2019-11-12 15:30:00+00
513	1	testTask	renren	0	\N	1	2019-11-12 16:00:00+00
514	1	testTask	renren	0	\N	0	2019-11-12 16:30:00+00
515	1	testTask	renren	0	\N	1	2019-11-12 17:00:00+00
516	1	testTask	renren	0	\N	1	2019-11-12 17:30:00+00
517	1	testTask	renren	0	\N	1	2019-11-12 18:00:00+00
518	1	testTask	renren	0	\N	0	2019-11-12 18:30:00+00
519	1	testTask	renren	0	\N	0	2019-11-12 19:00:00+00
520	1	testTask	renren	0	\N	1	2019-11-12 19:30:00+00
521	1	testTask	renren	0	\N	0	2019-11-12 20:00:00+00
522	1	testTask	renren	0	\N	0	2019-11-12 20:30:00+00
523	1	testTask	renren	0	\N	0	2019-11-12 21:00:00+00
524	1	testTask	renren	0	\N	1	2019-11-12 21:30:00+00
525	1	testTask	renren	0	\N	1	2019-11-12 22:00:00+00
526	1	testTask	renren	0	\N	1	2019-11-12 22:30:00+00
527	1	testTask	renren	0	\N	1	2019-11-12 23:00:00+00
528	1	testTask	renren	0	\N	1	2019-11-12 23:30:00+00
529	1	testTask	renren	0	\N	1	2019-11-13 09:30:00+00
530	1	testTask	renren	0	\N	1	2019-11-13 10:00:00+00
531	1	testTask	renren	0	\N	0	2019-11-13 10:30:00+00
532	1	testTask	renren	0	\N	1	2019-11-13 11:00:00+00
533	1	testTask	renren	0	\N	1	2019-11-13 11:30:00+00
534	1	testTask	renren	0	\N	0	2019-11-13 12:00:00+00
535	1	testTask	renren	0	\N	4	2019-11-13 12:30:00+00
536	1	testTask	renren	0	\N	0	2019-11-13 13:00:00+00
537	1	testTask	renren	0	\N	3	2019-11-13 13:30:00+00
538	1	testTask	renren	0	\N	0	2019-11-13 14:00:00+00
539	1	testTask	renren	0	\N	1	2019-11-13 14:30:00+00
540	1	testTask	renren	0	\N	0	2019-11-13 15:00:00+00
541	1	testTask	renren	0	\N	0	2019-11-13 15:30:00+00
542	1	testTask	renren	0	\N	1	2019-11-13 16:00:00+00
543	1	testTask	renren	0	\N	1	2019-11-13 16:30:00+00
544	1	testTask	renren	0	\N	1	2019-11-13 17:00:00+00
545	1	testTask	renren	0	\N	1	2019-11-13 17:30:00+00
546	1	testTask	renren	0	\N	1	2019-11-13 18:00:00+00
547	1	testTask	renren	0	\N	0	2019-11-13 18:30:00+00
548	1	testTask	renren	0	\N	1	2019-11-13 19:00:00+00
549	1	testTask	renren	0	\N	0	2019-11-13 19:30:00+00
550	1	testTask	renren	0	\N	3	2019-11-13 20:00:00+00
551	1	testTask	renren	0	\N	1	2019-11-13 20:30:00+00
552	1	testTask	renren	0	\N	0	2019-11-13 21:00:00+00
553	1	testTask	renren	0	\N	1	2019-11-13 21:30:00+00
554	1	testTask	renren	0	\N	1	2019-11-13 22:00:00+00
555	1	testTask	renren	0	\N	0	2019-11-13 22:30:00+00
556	1	testTask	renren	0	\N	1	2019-11-13 23:00:00+00
557	1	testTask	renren	0	\N	1	2019-11-13 23:30:00+00
558	1	testTask	renren	0	\N	10	2019-11-14 00:00:00+00
559	1	testTask	renren	0	\N	0	2019-11-14 11:00:00+00
560	1	testTask	renren	0	\N	1	2019-11-14 11:30:00+00
561	1	testTask	renren	0	\N	1	2019-11-14 12:00:00+00
562	1	testTask	renren	0	\N	1	2019-11-14 12:30:00+00
563	1	testTask	renren	0	\N	1	2019-11-14 13:00:00+00
564	1	testTask	renren	0	\N	0	2019-11-14 13:30:00+00
565	1	testTask	renren	0	\N	1	2019-11-14 14:00:00+00
566	1	testTask	renren	0	\N	1	2019-11-14 14:30:00+00
567	1	testTask	renren	0	\N	1	2019-11-14 15:00:00+00
568	1	testTask	renren	0	\N	1	2019-11-14 15:30:00+00
569	1	testTask	renren	0	\N	1	2019-11-14 16:00:00+00
570	1	testTask	renren	0	\N	0	2019-11-14 16:30:00+00
571	1	testTask	renren	0	\N	1	2019-11-14 17:00:00+00
572	1	testTask	renren	0	\N	1	2019-11-14 17:30:00+00
573	1	testTask	renren	0	\N	0	2019-11-14 18:00:00+00
574	1	testTask	renren	0	\N	1	2019-11-14 18:30:00+00
575	1	testTask	renren	0	\N	0	2019-11-14 19:00:00+00
576	1	testTask	renren	0	\N	1	2019-11-14 19:30:00+00
577	1	testTask	renren	0	\N	1	2019-11-14 20:00:00+00
578	1	testTask	renren	0	\N	1	2019-11-14 20:30:00+00
579	1	testTask	renren	0	\N	0	2019-11-14 21:00:00+00
580	1	testTask	renren	0	\N	1	2019-11-14 21:30:00+00
581	1	testTask	renren	0	\N	1	2019-11-14 22:00:00+00
582	1	testTask	renren	0	\N	1	2019-11-14 22:30:00+00
583	1	testTask	renren	0	\N	1	2019-11-14 23:00:00+00
584	1	testTask	renren	0	\N	1	2019-11-14 23:30:00+00
585	1	testTask	renren	0	\N	15	2019-11-15 00:00:00+00
586	1	testTask	renren	0	\N	1	2019-11-15 01:00:00+00
587	1	testTask	renren	0	\N	1	2019-11-15 01:30:00+00
588	1	testTask	renren	0	\N	1	2019-11-15 02:00:00+00
589	1	testTask	renren	0	\N	0	2019-11-15 02:30:00+00
590	1	testTask	renren	0	\N	1	2019-11-15 03:00:00+00
591	1	testTask	renren	0	\N	1	2019-11-15 03:30:00+00
592	1	testTask	renren	0	\N	1	2019-11-15 04:00:00+00
593	1	testTask	renren	0	\N	0	2019-11-15 04:30:00+00
594	1	testTask	renren	0	\N	0	2019-11-15 05:00:00+00
595	1	testTask	renren	0	\N	0	2019-11-15 05:30:00+00
596	1	testTask	renren	0	\N	1	2019-11-15 06:00:00+00
597	1	testTask	renren	0	\N	0	2019-11-15 06:30:00+00
598	1	testTask	renren	0	\N	1	2019-11-15 07:00:00+00
599	1	testTask	renren	0	\N	0	2019-11-15 07:30:00+00
600	1	testTask	renren	0	\N	1	2019-11-15 08:00:00+00
601	1	testTask	renren	0	\N	0	2019-11-15 08:30:00+00
602	1	testTask	renren	0	\N	0	2019-11-15 09:00:00+00
603	1	testTask	renren	0	\N	1	2019-11-15 09:30:00+00
604	1	testTask	renren	0	\N	1	2019-11-15 10:00:00+00
605	1	testTask	renren	0	\N	1	2019-11-15 11:00:00+00
606	1	testTask	renren	0	\N	1	2019-11-15 11:30:00+00
607	1	testTask	renren	0	\N	1	2019-11-15 12:00:00+00
608	1	testTask	renren	0	\N	1	2019-11-15 12:30:00+00
609	1	testTask	renren	0	\N	4	2019-11-15 13:00:00+00
610	1	testTask	renren	0	\N	0	2019-11-15 13:30:00+00
611	1	testTask	renren	0	\N	1	2019-11-15 14:00:00+00
612	1	testTask	renren	0	\N	1	2019-11-15 14:30:00+00
613	1	testTask	renren	0	\N	1	2019-11-15 15:00:00+00
614	1	testTask	renren	0	\N	1	2019-11-15 15:30:00+00
615	1	testTask	renren	0	\N	1	2019-11-15 16:00:00+00
616	1	testTask	renren	0	\N	1	2019-11-15 16:30:00+00
617	1	testTask	renren	0	\N	1	2019-11-15 17:00:00+00
618	1	testTask	renren	0	\N	1	2019-11-15 17:30:00+00
619	1	testTask	renren	0	\N	0	2019-11-15 18:00:00+00
620	1	testTask	renren	0	\N	1	2019-11-15 21:00:00+00
621	1	testTask	renren	0	\N	1	2019-11-15 21:30:00+00
622	1	testTask	renren	0	\N	1	2019-11-15 22:00:00+00
623	1	testTask	renren	0	\N	1	2019-11-15 22:30:00+00
624	1	testTask	renren	0	\N	1	2019-11-15 23:00:00+00
625	1	testTask	renren	0	\N	1	2019-11-15 23:30:00+00
626	1	testTask	renren	0	\N	1	2019-11-16 09:30:00+00
627	1	testTask	renren	0	\N	1	2019-11-16 10:00:00+00
628	1	testTask	renren	0	\N	1	2019-11-16 10:30:00+00
629	1	testTask	renren	0	\N	0	2019-11-16 11:00:00+00
630	1	testTask	renren	0	\N	0	2019-11-16 11:30:00+00
631	1	testTask	renren	0	\N	2	2019-11-16 12:00:00+00
632	1	testTask	renren	0	\N	1	2019-11-16 12:30:00+00
633	1	testTask	renren	0	\N	1	2019-11-16 13:00:00+00
634	1	testTask	renren	0	\N	1	2019-11-16 13:30:00+00
635	1	testTask	renren	0	\N	2	2019-11-16 14:00:00+00
636	1	testTask	renren	0	\N	1	2019-11-16 14:30:00+00
637	1	testTask	renren	0	\N	1	2019-11-16 15:00:00+00
638	1	testTask	renren	0	\N	1	2019-11-16 15:30:00+00
639	1	testTask	renren	0	\N	1	2019-11-16 16:00:00+00
640	1	testTask	renren	0	\N	1	2019-11-16 16:30:00+00
641	1	testTask	renren	0	\N	1	2019-11-16 17:00:00+00
642	1	testTask	renren	0	\N	0	2019-11-16 17:30:00+00
643	1	testTask	renren	0	\N	1	2019-11-16 18:00:00+00
644	1	testTask	renren	0	\N	1	2019-11-16 18:30:00+00
645	1	testTask	renren	0	\N	1	2019-11-16 19:00:00+00
646	1	testTask	renren	0	\N	0	2019-11-16 19:30:00+00
647	1	testTask	renren	0	\N	1	2019-11-16 20:00:00+00
648	1	testTask	renren	0	\N	0	2019-11-16 20:30:00+00
649	1	testTask	renren	0	\N	1	2019-11-16 21:00:00+00
650	1	testTask	renren	0	\N	1	2019-11-16 21:30:00+00
651	1	testTask	renren	0	\N	1	2019-11-16 22:00:00+00
652	1	testTask	renren	0	\N	1	2019-11-16 22:30:00+00
653	1	testTask	renren	0	\N	1	2019-11-16 23:00:00+00
654	1	testTask	renren	0	\N	1	2019-11-16 23:30:00+00
655	1	testTask	renren	0	\N	4	2019-11-17 00:00:00+00
656	1	testTask	renren	0	\N	0	2019-11-17 00:30:00+00
657	1	testTask	renren	0	\N	1	2019-11-17 12:00:00+00
658	1	testTask	renren	0	\N	1	2019-11-17 12:30:00+00
659	1	testTask	renren	0	\N	0	2019-11-17 13:00:00+00
660	1	testTask	renren	0	\N	0	2019-11-17 13:30:00+00
661	1	testTask	renren	0	\N	1	2019-11-17 14:00:00+00
662	1	testTask	renren	0	\N	1	2019-11-17 14:30:00+00
663	1	testTask	renren	0	\N	0	2019-11-17 15:00:00+00
664	1	testTask	renren	0	\N	1	2019-11-17 15:30:00+00
665	1	testTask	renren	0	\N	1	2019-11-17 16:00:00+00
666	1	testTask	renren	0	\N	1	2019-11-17 16:30:00+00
667	1	testTask	renren	0	\N	1	2019-11-17 17:00:00+00
668	1	testTask	renren	0	\N	1	2019-11-17 17:30:00+00
669	1	testTask	renren	0	\N	1	2019-11-17 18:00:00+00
670	1	testTask	renren	0	\N	1	2019-11-17 18:30:00+00
671	1	testTask	renren	0	\N	1	2019-11-17 19:00:00+00
672	1	testTask	renren	0	\N	1	2019-11-17 19:30:00+00
673	1	testTask	renren	0	\N	1	2019-11-17 20:00:00+00
674	1	testTask	renren	0	\N	1	2019-11-17 20:30:00+00
675	1	testTask	renren	0	\N	1	2019-11-17 21:00:00+00
676	1	testTask	renren	0	\N	1	2019-11-17 22:00:00+00
677	1	testTask	renren	0	\N	0	2019-11-17 22:30:00+00
678	1	testTask	renren	0	\N	1	2019-11-18 09:30:00+00
679	1	testTask	renren	0	\N	1	2019-11-18 10:00:00+00
680	1	testTask	renren	0	\N	1	2019-11-18 10:30:00+00
681	1	testTask	renren	0	\N	1	2019-11-18 11:00:00+00
682	1	testTask	renren	0	\N	1	2019-11-18 11:30:00+00
683	1	testTask	renren	0	\N	0	2019-11-18 12:00:00+00
684	1	testTask	renren	0	\N	1	2019-11-18 12:30:00+00
685	1	testTask	renren	0	\N	0	2019-11-18 13:00:00+00
686	1	testTask	renren	0	\N	1	2019-11-18 13:30:00+00
687	1	testTask	renren	0	\N	1	2019-11-18 14:00:00+00
688	1	testTask	renren	0	\N	0	2019-11-18 14:30:00+00
689	1	testTask	renren	0	\N	1	2019-11-18 15:00:00+00
690	1	testTask	renren	0	\N	0	2019-11-18 15:30:00+00
691	1	testTask	renren	0	\N	1	2019-11-18 16:00:00+00
692	1	testTask	renren	0	\N	1	2019-11-18 16:30:00+00
693	1	testTask	renren	0	\N	1	2019-11-18 17:00:00+00
694	1	testTask	renren	0	\N	2	2019-11-18 17:30:00+00
695	1	testTask	renren	0	\N	1	2019-11-18 18:00:00+00
696	1	testTask	renren	0	\N	0	2019-11-18 18:30:00+00
697	1	testTask	renren	0	\N	1	2019-11-18 19:00:00+00
698	1	testTask	renren	0	\N	1	2019-11-18 19:30:00+00
699	1	testTask	renren	0	\N	1	2019-11-18 20:30:00+00
700	1	testTask	renren	0	\N	1	2019-11-18 21:00:00+00
701	1	testTask	renren	0	\N	0	2019-11-18 21:30:00+00
702	1	testTask	renren	0	\N	1	2019-11-18 22:00:00+00
703	1	testTask	renren	0	\N	1	2019-11-18 22:30:00+00
704	1	testTask	renren	0	\N	0	2019-11-18 23:00:00+00
705	1	testTask	renren	0	\N	0	2019-11-18 23:30:00+00
706	1	testTask	renren	0	\N	14	2019-11-19 00:00:00+00
707	1	testTask	renren	0	\N	1	2019-11-19 09:30:00+00
708	1	testTask	renren	0	\N	1	2019-11-19 10:00:00+00
709	1	testTask	renren	0	\N	1	2019-11-19 10:30:00+00
710	1	testTask	renren	0	\N	1	2019-11-19 11:00:00+00
711	1	testTask	renren	0	\N	0	2019-11-19 11:30:00+00
712	1	testTask	renren	0	\N	1	2019-11-19 12:00:00+00
713	1	testTask	renren	0	\N	1	2019-11-19 12:30:00+00
714	1	testTask	renren	0	\N	1	2019-11-19 13:00:00+00
715	1	testTask	renren	0	\N	1	2019-11-19 13:30:00+00
716	1	testTask	renren	0	\N	1	2019-11-19 14:00:00+00
717	1	testTask	renren	0	\N	1	2019-11-19 14:30:00+00
718	1	testTask	renren	0	\N	0	2019-11-19 15:00:00+00
719	1	testTask	renren	0	\N	1	2019-11-19 15:30:00+00
720	1	testTask	renren	0	\N	0	2019-11-19 16:00:00+00
721	1	testTask	renren	0	\N	1	2019-11-19 16:30:00+00
722	1	testTask	renren	0	\N	1	2019-11-19 17:00:00+00
723	1	testTask	renren	0	\N	0	2019-11-19 17:30:00+00
724	1	testTask	renren	0	\N	0	2019-11-19 18:00:00+00
725	1	testTask	renren	0	\N	0	2019-11-19 18:30:00+00
726	1	testTask	renren	0	\N	0	2019-11-19 19:00:00+00
727	1	testTask	renren	0	\N	0	2019-11-19 19:30:00+00
728	1	testTask	renren	0	\N	1	2019-11-19 20:00:00+00
729	1	testTask	renren	0	\N	1	2019-11-19 20:30:00+00
730	1	testTask	renren	0	\N	0	2019-11-19 21:00:00+00
731	1	testTask	renren	0	\N	0	2019-11-19 21:30:00+00
732	1	testTask	renren	0	\N	0	2019-11-19 22:00:00+00
733	1	testTask	renren	0	\N	1	2019-11-19 22:30:00+00
734	1	testTask	renren	0	\N	1	2019-11-19 23:00:00+00
735	1	testTask	renren	0	\N	0	2019-11-19 23:30:00+00
736	1	testTask	renren	0	\N	4	2019-11-20 00:00:00+00
737	1	testTask	renren	0	\N	1	2019-11-20 09:30:00+00
738	1	testTask	renren	0	\N	0	2019-11-20 10:00:00+00
739	1	testTask	renren	0	\N	1	2019-11-20 10:30:00+00
740	1	testTask	renren	0	\N	1	2019-11-20 11:00:00+00
741	1	testTask	renren	0	\N	0	2019-11-20 11:30:00+00
742	1	testTask	renren	0	\N	1	2019-11-20 12:00:00+00
743	1	testTask	renren	0	\N	1	2019-11-20 12:30:00+00
744	1	testTask	renren	0	\N	1	2019-11-20 13:00:00+00
745	1	testTask	renren	0	\N	1	2019-11-20 13:30:00+00
746	1	testTask	renren	0	\N	1	2019-11-20 14:00:00+00
747	1	testTask	renren	0	\N	0	2019-11-20 15:00:00+00
748	1	testTask	renren	0	\N	1	2019-11-20 15:30:00+00
749	1	testTask	renren	0	\N	1	2019-11-20 16:00:00+00
750	1	testTask	renren	0	\N	0	2019-11-20 16:30:00+00
751	1	testTask	renren	0	\N	1	2019-11-20 17:00:00+00
752	1	testTask	renren	0	\N	1	2019-11-20 17:30:00+00
753	1	testTask	renren	0	\N	1	2019-11-20 18:00:00+00
754	1	testTask	renren	0	\N	1	2019-11-20 18:30:00+00
755	1	testTask	renren	0	\N	1	2019-11-20 19:00:00+00
756	1	testTask	renren	0	\N	0	2019-11-20 19:30:00+00
757	1	testTask	renren	0	\N	1	2019-11-20 20:00:00+00
758	1	testTask	renren	0	\N	0	2019-11-20 20:30:00+00
759	1	testTask	renren	0	\N	0	2019-11-20 21:00:00+00
760	1	testTask	renren	0	\N	0	2019-11-20 21:30:00+00
761	1	testTask	renren	0	\N	1	2019-11-20 22:00:00+00
762	1	testTask	renren	0	\N	1	2019-11-20 22:30:00+00
763	1	testTask	renren	0	\N	1	2019-11-20 23:00:00+00
764	1	testTask	renren	0	\N	0	2019-11-20 23:30:00+00
765	1	testTask	renren	0	\N	1	2019-11-21 09:30:00+00
766	1	testTask	renren	0	\N	1	2019-11-21 10:00:00+00
767	1	testTask	renren	0	\N	1	2019-11-21 10:30:00+00
768	1	testTask	renren	0	\N	1	2019-11-21 11:00:00+00
769	1	testTask	renren	0	\N	0	2019-11-21 11:30:00+00
770	1	testTask	renren	0	\N	1	2019-11-21 12:00:00+00
771	1	testTask	renren	0	\N	1	2019-11-21 12:30:00+00
772	1	testTask	renren	0	\N	1	2019-11-21 13:00:00+00
773	1	testTask	renren	0	\N	1	2019-11-21 13:30:00+00
774	1	testTask	renren	0	\N	0	2019-11-21 14:00:00+00
775	1	testTask	renren	0	\N	1	2019-11-21 14:30:00+00
776	1	testTask	renren	0	\N	1	2019-11-21 15:00:00+00
777	1	testTask	renren	0	\N	0	2019-11-21 15:30:00+00
778	1	testTask	renren	0	\N	0	2019-11-21 16:00:00+00
779	1	testTask	renren	0	\N	0	2019-11-21 16:30:00+00
780	1	testTask	renren	0	\N	1	2019-11-21 17:00:00+00
781	1	testTask	renren	0	\N	1	2019-11-21 17:30:00+00
782	1	testTask	renren	0	\N	1	2019-11-21 18:00:00+00
783	1	testTask	renren	0	\N	1	2019-11-21 18:30:00+00
784	1	testTask	renren	0	\N	1	2019-11-21 19:00:00+00
785	1	testTask	renren	0	\N	1	2019-11-21 19:30:00+00
786	1	testTask	renren	0	\N	1	2019-11-21 20:00:00+00
787	1	testTask	renren	0	\N	0	2019-11-21 20:30:00+00
788	1	testTask	renren	0	\N	1	2019-11-21 21:00:00+00
789	1	testTask	renren	0	\N	1	2019-11-21 21:30:00+00
790	1	testTask	renren	0	\N	1	2019-11-21 22:00:00+00
791	1	testTask	renren	0	\N	1	2019-11-21 22:30:00+00
792	1	testTask	renren	0	\N	1	2019-11-22 12:00:00+00
793	1	testTask	renren	0	\N	0	2019-11-22 12:30:00+00
794	1	testTask	renren	0	\N	0	2019-11-22 13:00:00+00
795	1	testTask	renren	0	\N	1	2019-11-22 13:30:00+00
796	1	testTask	renren	0	\N	0	2019-11-22 14:00:00+00
797	1	testTask	renren	0	\N	1	2019-11-22 14:30:00+00
798	1	testTask	renren	0	\N	0	2019-11-22 15:00:00+00
799	1	testTask	renren	0	\N	1	2019-11-22 15:30:00+00
800	1	testTask	renren	0	\N	1	2019-11-22 16:00:00+00
801	1	testTask	renren	0	\N	1	2019-11-22 16:30:00+00
802	1	testTask	renren	0	\N	1	2019-11-22 17:00:00+00
803	1	testTask	renren	0	\N	1	2019-11-22 17:30:00+00
804	1	testTask	renren	0	\N	1	2019-11-22 18:00:00+00
805	1	testTask	renren	0	\N	1	2019-11-22 18:30:00+00
806	1	testTask	renren	0	\N	4	2019-11-22 19:00:00+00
807	1	testTask	renren	0	\N	9	2019-11-22 19:30:00+00
808	1	testTask	renren	0	\N	2	2019-11-22 20:00:00+00
809	1	testTask	renren	0	\N	1	2019-11-22 20:30:00+00
810	1	testTask	renren	0	\N	2	2019-11-22 21:00:00+00
811	1	testTask	renren	0	\N	1	2019-11-22 21:30:00+00
812	1	testTask	renren	0	\N	1	2019-11-22 22:00:00+00
813	1	testTask	renren	0	\N	2	2019-11-22 22:30:00+00
814	1	testTask	renren	0	\N	2	2019-11-22 23:00:00+00
815	1	testTask	renren	0	\N	2	2019-11-22 23:30:00+00
816	1	testTask	renren	0	\N	29	2019-11-23 00:00:00+00
817	1	testTask	renren	0	\N	1	2019-11-24 14:00:00+00
818	1	testTask	renren	0	\N	0	2019-11-24 14:30:00+00
819	1	testTask	renren	0	\N	1	2019-11-24 15:00:00+00
820	1	testTask	renren	0	\N	1	2019-11-24 15:30:00+00
821	1	testTask	renren	0	\N	1	2019-11-24 16:00:00+00
822	1	testTask	renren	0	\N	1	2019-11-24 16:30:00+00
823	1	testTask	renren	0	\N	1	2019-11-24 17:00:00+00
824	1	testTask	renren	0	\N	1	2019-11-24 17:30:00+00
825	1	testTask	renren	0	\N	1	2019-11-24 18:00:00+00
826	1	testTask	renren	0	\N	1	2019-11-24 18:30:00+00
827	1	testTask	renren	0	\N	1	2019-11-24 19:00:00+00
828	1	testTask	renren	0	\N	1	2019-11-24 19:30:00+00
829	1	testTask	renren	0	\N	1	2019-11-24 20:00:00+00
830	1	testTask	renren	0	\N	1	2019-11-24 21:00:00+00
831	1	testTask	renren	0	\N	2	2019-11-25 10:00:00+00
832	1	testTask	renren	0	\N	0	2019-11-25 10:30:00+00
833	1	testTask	renren	0	\N	1	2019-11-25 11:00:00+00
834	1	testTask	renren	0	\N	1	2019-11-25 11:30:00+00
835	1	testTask	renren	0	\N	1	2019-11-25 12:00:00+00
836	1	testTask	renren	0	\N	1	2019-11-25 12:30:00+00
837	1	testTask	renren	0	\N	1	2019-11-25 13:00:00+00
838	1	testTask	renren	0	\N	1	2019-11-25 13:30:00+00
839	1	testTask	renren	0	\N	1	2019-11-25 14:00:00+00
840	1	testTask	renren	0	\N	1	2019-11-25 14:30:00+00
841	1	testTask	renren	0	\N	1	2019-11-25 15:00:00+00
842	1	testTask	renren	0	\N	1	2019-11-25 15:30:00+00
843	1	testTask	renren	0	\N	4	2019-11-25 16:00:00+00
844	1	testTask	renren	0	\N	2	2019-11-25 16:30:00+00
845	1	testTask	renren	0	\N	2	2019-11-25 17:00:00+00
846	1	testTask	renren	0	\N	2	2019-11-25 17:30:00+00
847	1	testTask	renren	0	\N	1	2019-11-25 18:00:00+00
848	1	testTask	renren	0	\N	1	2019-11-25 18:30:00+00
849	1	testTask	renren	0	\N	1	2019-11-25 19:00:00+00
850	1	testTask	renren	0	\N	2	2019-11-25 22:30:00+00
851	1	testTask	renren	0	\N	1	2019-11-25 23:00:00+00
852	1	testTask	renren	0	\N	2	2019-11-25 23:30:00+00
853	1	testTask	renren	0	\N	1	2019-11-26 10:00:00+00
854	1	testTask	renren	0	\N	1	2019-11-26 10:30:00+00
855	1	testTask	renren	0	\N	1	2019-11-26 11:00:00+00
856	1	testTask	renren	0	\N	6	2019-11-26 11:30:00+00
857	1	testTask	renren	0	\N	1	2019-11-26 12:00:00+00
858	1	testTask	renren	0	\N	0	2019-11-26 12:30:00+00
859	1	testTask	renren	0	\N	1	2019-11-26 13:00:00+00
860	1	testTask	renren	0	\N	0	2019-11-26 13:30:00+00
861	1	testTask	renren	0	\N	0	2019-11-26 14:00:00+00
862	1	testTask	renren	0	\N	0	2019-11-26 14:30:00+00
863	1	testTask	renren	0	\N	0	2019-11-26 15:00:00+00
864	1	testTask	renren	0	\N	1	2019-11-26 15:30:00+00
865	1	testTask	renren	0	\N	5	2019-11-26 16:00:00+00
866	1	testTask	renren	0	\N	1	2019-11-26 16:30:00+00
867	1	testTask	renren	0	\N	4	2019-11-26 17:00:00+00
868	1	testTask	renren	0	\N	1	2019-11-26 17:30:00+00
869	1	testTask	renren	0	\N	0	2019-11-26 18:00:00+00
870	1	testTask	renren	0	\N	2	2019-11-26 18:30:00+00
871	1	testTask	renren	0	\N	1	2019-11-26 19:00:00+00
872	1	testTask	renren	0	\N	1	2019-11-26 19:30:00+00
873	1	testTask	renren	0	\N	0	2019-11-26 20:00:00+00
874	1	testTask	renren	0	\N	1	2019-11-26 20:30:00+00
875	1	testTask	renren	0	\N	1	2019-11-26 21:00:00+00
876	1	testTask	renren	0	\N	1	2019-11-27 11:00:00+00
877	1	testTask	renren	0	\N	1	2019-11-27 11:30:00+00
878	1	testTask	renren	0	\N	1	2019-11-27 12:00:00+00
879	1	testTask	renren	0	\N	4	2019-11-27 12:30:00+00
880	1	testTask	renren	0	\N	1	2019-11-27 13:00:00+00
881	1	testTask	renren	0	\N	1	2019-11-27 13:30:00+00
882	1	testTask	renren	0	\N	1	2019-11-27 14:00:00+00
883	1	testTask	renren	0	\N	2	2019-11-27 14:30:00+00
884	1	testTask	renren	0	\N	0	2019-11-27 15:00:00+00
885	1	testTask	renren	0	\N	1	2019-11-27 15:30:00+00
886	1	testTask	renren	0	\N	1	2019-11-27 16:00:00+00
887	1	testTask	renren	0	\N	1	2019-11-27 16:30:00+00
888	1	testTask	renren	0	\N	2	2019-11-27 18:00:00+00
889	1	testTask	renren	0	\N	1	2019-11-27 18:30:00+00
890	1	testTask	renren	0	\N	1	2019-11-27 19:00:00+00
891	1	testTask	renren	0	\N	0	2019-11-27 19:30:00+00
892	1	testTask	renren	0	\N	0	2019-11-27 20:00:00+00
893	1	testTask	renren	0	\N	0	2019-11-27 20:30:00+00
894	1	testTask	renren	0	\N	1	2019-11-27 21:00:00+00
895	1	testTask	renren	0	\N	1	2019-11-27 21:30:00+00
896	1	testTask	renren	0	\N	1	2019-11-27 22:00:00+00
897	1	testTask	renren	0	\N	1	2019-11-27 22:30:00+00
898	1	testTask	renren	0	\N	1	2019-11-27 23:00:00+00
899	1	testTask	renren	0	\N	37	2019-11-27 23:30:00+00
900	1	testTask	renren	0	\N	508	2019-11-28 00:00:00+00
901	1	testTask	renren	0	\N	20	2019-11-28 00:30:00+00
902	1	testTask	renren	0	\N	1	2019-11-28 10:00:00+00
903	1	testTask	renren	0	\N	1	2019-11-28 10:30:00+00
904	1	testTask	renren	0	\N	2	2019-11-28 11:00:00+00
905	1	testTask	renren	0	\N	0	2019-11-28 11:30:00+00
906	1	testTask	renren	0	\N	1	2019-11-28 12:00:00+00
907	1	testTask	renren	0	\N	0	2019-11-28 12:30:00+00
908	1	testTask	renren	0	\N	1	2019-11-28 13:00:00+00
909	1	testTask	renren	0	\N	1	2019-11-28 13:30:00+00
910	1	testTask	renren	0	\N	1	2019-11-28 14:00:00+00
911	1	testTask	renren	0	\N	0	2019-11-28 14:30:00+00
912	1	testTask	renren	0	\N	1	2019-11-28 15:00:00+00
913	1	testTask	renren	0	\N	1	2019-11-28 15:30:00+00
914	1	testTask	renren	0	\N	1	2019-11-28 16:00:00+00
915	1	testTask	renren	0	\N	1	2019-11-28 16:30:00+00
916	1	testTask	renren	0	\N	0	2019-11-28 17:00:00+00
917	1	testTask	renren	0	\N	1	2019-11-28 17:30:00+00
918	1	testTask	renren	0	\N	1	2019-11-28 18:00:00+00
919	1	testTask	renren	0	\N	1	2019-11-28 18:30:00+00
920	1	testTask	renren	0	\N	1	2019-11-28 19:00:00+00
921	1	testTask	renren	0	\N	1	2019-11-28 19:30:00+00
922	1	testTask	renren	0	\N	1	2019-11-28 20:00:00+00
923	1	testTask	renren	0	\N	1	2019-11-28 20:30:00+00
924	1	testTask	renren	0	\N	0	2019-11-28 21:00:00+00
925	1	testTask	renren	0	\N	0	2019-11-28 21:30:00+00
926	1	testTask	renren	0	\N	1	2019-11-28 22:00:00+00
927	1	testTask	renren	0	\N	0	2019-11-28 22:30:00+00
928	1	testTask	renren	0	\N	1	2019-11-29 09:30:00+00
929	1	testTask	renren	0	\N	0	2019-11-29 10:00:00+00
930	1	testTask	renren	0	\N	0	2019-11-29 10:30:00+00
931	1	testTask	renren	0	\N	0	2019-11-29 11:00:00+00
932	1	testTask	renren	0	\N	1	2019-11-29 11:30:00+00
933	1	testTask	renren	0	\N	0	2019-11-29 12:00:00+00
934	1	testTask	renren	0	\N	1	2019-11-29 12:30:00+00
935	1	testTask	renren	0	\N	0	2019-11-29 13:00:00+00
936	1	testTask	renren	0	\N	1	2019-11-29 13:30:00+00
937	1	testTask	renren	0	\N	0	2019-11-29 14:00:00+00
938	1	testTask	renren	0	\N	0	2019-11-29 14:30:00+00
939	1	testTask	renren	0	\N	1	2019-11-29 15:00:00+00
940	1	testTask	renren	0	\N	1	2019-11-29 15:30:00+00
941	1	testTask	renren	0	\N	0	2019-11-29 16:00:00+00
942	1	testTask	renren	0	\N	3	2019-11-29 16:30:00+00
943	1	testTask	renren	0	\N	1	2019-11-29 17:00:00+00
944	1	testTask	renren	0	\N	1	2019-11-29 17:30:00+00
945	1	testTask	renren	0	\N	1	2019-11-29 18:00:00+00
946	1	testTask	renren	0	\N	1	2019-11-29 18:30:00+00
947	1	testTask	renren	0	\N	1	2019-11-29 19:00:00+00
948	1	testTask	renren	0	\N	1	2019-11-29 19:30:00+00
949	1	testTask	renren	0	\N	0	2019-11-29 20:00:00+00
950	1	testTask	renren	0	\N	1	2019-11-30 17:00:00+00
951	1	testTask	renren	0	\N	1	2019-11-30 17:30:00+00
952	1	testTask	renren	0	\N	1	2019-11-30 18:00:00+00
953	1	testTask	renren	0	\N	1	2019-11-30 18:30:00+00
954	1	testTask	renren	0	\N	1	2019-11-30 19:00:00+00
955	1	testTask	renren	0	\N	1	2019-11-30 19:30:00+00
956	1	testTask	renren	0	\N	0	2019-11-30 20:00:00+00
957	1	testTask	renren	0	\N	1	2019-11-30 20:30:00+00
958	1	testTask	renren	0	\N	1	2019-11-30 21:00:00+00
959	1	testTask	renren	0	\N	3	2019-12-01 17:00:00+00
960	1	testTask	renren	0	\N	1	2019-12-01 17:30:00+00
961	1	testTask	renren	0	\N	2	2019-12-01 18:00:00+00
962	1	testTask	renren	0	\N	1	2019-12-01 18:30:00+00
963	1	testTask	renren	0	\N	1	2019-12-01 19:00:00+00
964	1	testTask	renren	0	\N	0	2019-12-01 19:30:00+00
965	1	testTask	renren	0	\N	1	2019-12-01 20:00:00+00
966	1	testTask	renren	0	\N	1	2019-12-01 22:00:00+00
967	1	testTask	renren	0	\N	1	2019-12-01 22:30:00+00
968	1	testTask	renren	0	\N	1	2019-12-01 23:00:00+00
969	1	testTask	renren	0	\N	0	2019-12-01 23:30:00+00
970	1	testTask	renren	0	\N	7	2019-12-02 00:00:00+00
971	1	testTask	renren	0	\N	1	2019-12-02 00:30:00+00
972	1	testTask	renren	0	\N	1	2019-12-02 01:00:00+00
973	1	testTask	renren	0	\N	1	2019-12-02 01:30:00+00
974	1	testTask	renren	0	\N	0	2019-12-02 02:00:00+00
975	1	testTask	renren	0	\N	1	2019-12-02 02:30:00+00
976	1	testTask	renren	0	\N	1	2019-12-02 03:00:00+00
977	1	testTask	renren	0	\N	1	2019-12-02 03:30:00+00
978	1	testTask	renren	0	\N	0	2019-12-02 04:00:00+00
979	1	testTask	renren	0	\N	0	2019-12-02 04:30:00+00
980	1	testTask	renren	0	\N	0	2019-12-02 05:00:00+00
981	1	testTask	renren	0	\N	1	2019-12-02 05:30:00+00
982	1	testTask	renren	0	\N	1	2019-12-02 06:00:00+00
983	1	testTask	renren	0	\N	0	2019-12-02 06:30:00+00
984	1	testTask	renren	0	\N	0	2019-12-02 07:00:00+00
985	1	testTask	renren	0	\N	1	2019-12-02 07:30:00+00
986	1	testTask	renren	0	\N	1	2019-12-02 08:00:00+00
987	1	testTask	renren	0	\N	0	2019-12-02 08:30:00+00
988	1	testTask	renren	0	\N	1	2019-12-02 09:00:00+00
989	1	testTask	renren	0	\N	1	2019-12-02 09:30:00+00
990	1	testTask	renren	0	\N	0	2019-12-02 10:00:00+00
991	1	testTask	renren	0	\N	1	2019-12-02 10:30:00+00
992	1	testTask	renren	0	\N	1	2019-12-02 11:00:00+00
993	1	testTask	renren	0	\N	1	2019-12-02 11:30:00+00
994	1	testTask	renren	0	\N	1	2019-12-02 12:00:00+00
995	1	testTask	renren	0	\N	0	2019-12-02 12:30:00+00
996	1	testTask	renren	0	\N	0	2019-12-02 13:00:00+00
997	1	testTask	renren	0	\N	1	2019-12-02 13:30:00+00
998	1	testTask	renren	0	\N	1	2019-12-02 14:00:00+00
999	1	testTask	renren	0	\N	1	2019-12-02 14:30:00+00
1000	1	testTask	renren	0	\N	1	2019-12-02 15:00:00+00
1001	1	testTask	renren	0	\N	0	2019-12-02 15:30:00+00
1002	1	testTask	renren	0	\N	1	2019-12-02 16:00:00+00
1003	1	testTask	renren	0	\N	1	2019-12-02 16:30:00+00
1004	1	testTask	renren	0	\N	2	2019-12-02 17:00:00+00
1005	1	testTask	renren	0	\N	0	2019-12-02 17:30:00+00
1006	1	testTask	renren	0	\N	1	2019-12-11 13:30:00+00
1007	1	testTask	renren	0	\N	1	2019-12-11 14:00:00+00
1008	1	testTask	renren	0	\N	1	2019-12-11 14:30:00+00
1009	1	testTask	renren	0	\N	1	2019-12-11 15:00:00+00
1010	1	testTask	renren	0	\N	1	2019-12-11 15:30:00+00
1011	1	testTask	renren	0	\N	0	2019-12-11 16:00:00+00
1012	1	testTask	renren	0	\N	0	2019-12-11 16:30:00+00
1013	1	testTask	renren	0	\N	1	2019-12-11 17:00:00+00
1014	1	testTask	renren	0	\N	1	2019-12-11 17:30:00+00
1015	1	testTask	renren	0	\N	0	2019-12-11 18:00:00+00
1016	1	testTask	renren	0	\N	1	2019-12-11 18:30:00+00
1017	1	testTask	renren	0	\N	0	2019-12-11 19:00:00+00
1018	1	testTask	renren	0	\N	0	2019-12-11 19:30:00+00
1019	1	testTask	renren	0	\N	1	2019-12-11 20:00:00+00
1020	1	testTask	renren	0	\N	1	2019-12-11 20:30:00+00
1021	1	testTask	renren	0	\N	1	2019-12-11 21:00:00+00
1022	1	testTask	renren	0	\N	1	2019-12-11 21:30:00+00
1023	1	testTask	renren	0	\N	1	2019-12-12 18:00:00+00
1024	1	testTask	renren	0	\N	0	2019-12-13 11:00:00+00
1025	1	testTask	renren	0	\N	1	2019-12-13 11:30:00+00
1026	1	testTask	renren	0	\N	1	2019-12-13 12:00:00+00
1027	1	testTask	renren	0	\N	0	2019-12-13 12:30:00+00
1028	1	testTask	renren	0	\N	1	2019-12-13 13:00:00+00
1029	1	testTask	renren	0	\N	2	2019-12-13 13:30:00+00
1030	1	testTask	renren	0	\N	1	2019-12-13 14:00:00+00
1031	1	testTask	renren	0	\N	1	2019-12-13 14:30:00+00
1032	1	testTask	renren	0	\N	1	2019-12-13 15:00:00+00
1033	1	testTask	renren	0	\N	1	2019-12-13 15:30:00+00
1034	1	testTask	renren	0	\N	1	2019-12-13 16:00:00+00
1035	1	testTask	renren	0	\N	1	2019-12-13 16:30:00+00
1036	1	testTask	renren	0	\N	0	2019-12-13 17:00:00+00
1037	1	testTask	renren	0	\N	1	2019-12-13 17:30:00+00
1038	1	testTask	renren	0	\N	0	2019-12-13 18:00:00+00
1039	1	testTask	renren	0	\N	1	2019-12-13 18:30:00+00
1040	1	testTask	renren	0	\N	2	2019-12-13 19:00:00+00
1041	1	testTask	renren	0	\N	1	2019-12-13 19:30:00+00
1042	1	testTask	renren	0	\N	0	2019-12-13 20:00:00+00
1043	1	testTask	renren	0	\N	1	2019-12-13 20:30:00+00
1044	1	testTask	renren	0	\N	1	2019-12-13 21:00:00+00
1045	1	testTask	renren	0	\N	1	2019-12-15 23:30:00+00
1046	1	testTask	renren	0	\N	5	2019-12-16 00:00:00+00
1047	1	testTask	renren	0	\N	1	2019-12-16 00:30:00+00
1048	1	testTask	renren	0	\N	0	2019-12-16 09:30:00+00
1049	1	testTask	renren	0	\N	1	2019-12-16 10:00:00+00
1050	1	testTask	renren	0	\N	1	2019-12-16 10:30:00+00
1051	1	testTask	renren	0	\N	0	2019-12-16 11:00:00+00
1052	1	testTask	renren	0	\N	1	2019-12-16 11:30:00+00
1053	1	testTask	renren	0	\N	1	2019-12-16 12:00:00+00
1054	1	testTask	renren	0	\N	1	2019-12-16 12:30:00+00
1055	1	testTask	renren	0	\N	1	2019-12-16 13:00:00+00
1056	1	testTask	renren	0	\N	1	2019-12-16 13:30:00+00
1057	1	testTask	renren	0	\N	1	2019-12-16 14:00:00+00
1058	1	testTask	renren	0	\N	0	2019-12-16 14:30:00+00
1059	1	testTask	renren	0	\N	0	2019-12-16 15:00:00+00
1060	1	testTask	renren	0	\N	3	2019-12-16 15:30:00+00
1061	1	testTask	renren	0	\N	1	2019-12-16 16:00:00+00
1062	1	testTask	renren	0	\N	1	2019-12-16 16:30:00+00
1063	1	testTask	renren	0	\N	1	2019-12-16 17:00:00+00
1064	1	testTask	renren	0	\N	1	2019-12-18 23:00:00+00
1065	1	testTask	renren	0	\N	1	2019-12-19 10:00:00+00
1066	1	testTask	renren	0	\N	0	2019-12-19 10:30:00+00
1067	1	testTask	renren	0	\N	1	2019-12-19 11:00:00+00
1068	1	testTask	renren	0	\N	2	2019-12-19 11:30:00+00
1069	1	testTask	renren	0	\N	0	2019-12-19 12:00:00+00
1070	1	testTask	renren	0	\N	1	2019-12-19 12:30:00+00
1071	1	testTask	renren	0	\N	1	2019-12-19 13:00:00+00
1072	1	testTask	renren	0	\N	1	2019-12-19 13:30:00+00
1073	1	testTask	renren	0	\N	1	2019-12-19 14:00:00+00
1074	1	testTask	renren	0	\N	0	2019-12-19 14:30:00+00
1075	1	testTask	renren	0	\N	1	2019-12-19 15:00:00+00
1076	1	testTask	renren	0	\N	0	2019-12-19 15:30:00+00
1077	1	testTask	renren	0	\N	1	2019-12-19 16:00:00+00
1078	1	testTask	renren	0	\N	0	2019-12-19 16:30:00+00
1079	1	testTask	renren	0	\N	2	2019-12-19 17:00:00+00
1080	1	testTask	renren	0	\N	1	2019-12-19 17:30:00+00
1081	1	testTask	renren	0	\N	1	2019-12-19 18:00:00+00
1082	1	testTask	renren	0	\N	1	2019-12-19 18:30:00+00
1083	1	testTask	renren	0	\N	1	2019-12-19 19:00:00+00
1084	1	testTask	renren	0	\N	2	2019-12-19 19:30:00+00
1085	1	testTask	renren	0	\N	1	2019-12-19 20:00:00+00
1086	1	testTask	renren	0	\N	1	2019-12-19 20:30:00+00
1087	1	testTask	renren	0	\N	2	2019-12-19 21:00:00+00
1088	1	testTask	renren	0	\N	1	2019-12-19 21:30:00+00
1089	1	testTask	renren	0	\N	3	2019-12-19 22:00:00+00
1090	1	testTask	renren	0	\N	0	2019-12-19 22:30:00+00
1091	1	testTask	renren	0	\N	1	2019-12-19 23:00:00+00
1092	1	testTask	renren	0	\N	1	2019-12-19 23:30:00+00
1093	1	testTask	renren	0	\N	14	2019-12-20 00:00:00+00
1094	1	testTask	renren	0	\N	1	2019-12-20 11:00:00+00
1095	1	testTask	renren	0	\N	1	2019-12-20 11:30:00+00
1096	1	testTask	renren	0	\N	1	2019-12-20 12:00:00+00
1097	1	testTask	renren	0	\N	1	2019-12-20 12:30:00+00
1098	1	testTask	renren	0	\N	0	2019-12-20 13:00:00+00
1099	1	testTask	renren	0	\N	0	2019-12-20 13:30:00+00
1100	1	testTask	renren	0	\N	1	2019-12-20 14:00:00+00
1101	1	testTask	renren	0	\N	1	2019-12-20 14:30:00+00
1102	1	testTask	renren	0	\N	1	2019-12-20 15:00:00+00
1103	1	testTask	renren	0	\N	0	2020-01-09 15:00:00+00
1104	1	testTask	renren	0	\N	1	2020-01-09 15:30:00+00
1105	1	testTask	renren	0	\N	1	2020-01-09 16:00:00+00
1106	1	testTask	renren	0	\N	1	2020-01-09 16:30:00+00
1107	1	testTask	renren	0	\N	2	2020-01-09 17:00:00+00
1108	1	testTask	renren	0	\N	1	2020-01-09 17:30:00+00
1109	1	testTask	renren	0	\N	0	2020-01-09 18:00:00+00
1110	1	testTask	renren	0	\N	1	2020-01-09 18:30:00+00
1111	1	testTask	renren	0	\N	1	2020-01-09 19:00:00+00
1112	1	testTask	renren	0	\N	0	2020-01-09 19:30:00+00
1113	1	testTask	renren	0	\N	1	2020-01-09 20:00:00+00
1114	1	testTask	renren	0	\N	1	2020-01-09 20:30:00+00
1115	1	testTask	renren	0	\N	2	2020-01-09 21:00:00+00
1116	1	testTask	renren	0	\N	1	2020-01-09 21:30:00+00
1117	1	testTask	renren	0	\N	1	2020-01-09 22:00:00+00
1118	1	testTask	renren	0	\N	2	2020-01-09 22:30:00+00
1119	1	testTask	renren	0	\N	2	2020-01-09 23:00:00+00
1120	1	testTask	renren	0	\N	1	2020-01-09 23:30:00+00
1121	1	testTask	renren	0	\N	14	2020-01-10 00:00:00+00
1122	1	testTask	renren	0	\N	1	2020-01-27 12:00:00+00
1123	1	testTask	renren	0	\N	1	2020-01-27 12:30:00+00
1124	1	testTask	renren	0	\N	1	2020-01-27 13:00:00+00
1125	1	testTask	renren	0	\N	1	2020-01-27 13:30:00+00
1126	1	testTask	renren	0	\N	1	2020-01-27 14:00:00+00
1127	1	testTask	renren	0	\N	1	2020-01-27 14:30:00+00
1128	1	testTask	renren	0	\N	0	2020-01-27 15:00:00+00
1129	1	testTask	renren	0	\N	2	2020-01-28 11:30:00+00
1130	1	testTask	renren	0	\N	1	2020-01-28 12:00:00+00
1131	1	testTask	renren	0	\N	1	2020-01-28 12:30:00+00
1132	1	testTask	renren	0	\N	1	2020-01-28 13:00:00+00
1133	1	testTask	renren	0	\N	0	2020-01-28 13:30:00+00
1134	1	testTask	renren	0	\N	10	2020-01-28 14:00:00+00
1135	1	testTask	renren	0	\N	1	2020-01-28 14:30:00+00
1136	1	testTask	renren	0	\N	1	2020-01-28 15:00:00+00
1137	1	testTask	renren	0	\N	1	2020-01-28 15:30:00+00
1138	1	testTask	renren	0	\N	1	2020-01-28 16:00:00+00
1139	1	testTask	renren	0	\N	1	2020-01-28 16:30:00+00
1140	1	testTask	renren	0	\N	0	2020-01-28 17:00:00+00
1141	1	testTask	renren	0	\N	0	2020-01-28 17:30:00+00
1142	1	testTask	renren	0	\N	1	2020-01-28 18:00:00+00
1143	1	testTask	renren	0	\N	1	2020-01-28 18:30:00+00
1144	1	testTask	renren	0	\N	1	2020-01-28 19:00:00+00
1145	1	testTask	renren	0	\N	0	2020-01-28 19:30:00+00
1146	1	testTask	renren	0	\N	0	2020-01-28 20:00:00+00
1147	1	testTask	renren	0	\N	1	2020-01-29 11:30:00+00
1148	1	testTask	renren	0	\N	1	2020-01-29 12:30:00+00
1149	1	testTask	renren	0	\N	12	2020-01-29 13:00:00+00
1150	1	testTask	renren	0	\N	1	2020-01-29 13:30:00+00
1151	1	testTask	renren	0	\N	1	2020-01-29 14:00:00+00
1152	1	testTask	renren	0	\N	1	2020-01-29 14:30:00+00
1153	1	testTask	renren	0	\N	1	2020-01-29 15:00:00+00
1154	1	testTask	renren	0	\N	1	2020-01-29 15:30:00+00
1155	1	testTask	renren	0	\N	0	2020-01-29 16:00:00+00
1156	1	testTask	renren	0	\N	1	2020-01-29 16:30:00+00
1157	1	testTask	renren	0	\N	0	2020-01-29 17:00:00+00
1158	1	testTask	renren	0	\N	1	2020-01-30 11:00:00+00
1159	1	testTask	renren	0	\N	2	2020-01-30 11:30:00+00
1160	1	testTask	renren	0	\N	1	2020-01-30 12:00:00+00
1161	1	testTask	renren	0	\N	1	2020-01-30 12:30:00+00
1162	1	testTask	renren	0	\N	1	2020-01-30 13:00:00+00
1163	1	testTask	renren	0	\N	3	2020-01-30 13:30:00+00
1164	1	testTask	renren	0	\N	2	2020-01-30 14:00:00+00
1165	1	testTask	renren	0	\N	1	2020-01-30 14:30:00+00
1166	1	testTask	renren	0	\N	1	2020-01-30 15:00:00+00
1167	1	testTask	renren	0	\N	1	2020-01-30 15:30:00+00
1168	1	testTask	renren	0	\N	1	2020-01-30 16:00:00+00
1169	1	testTask	renren	0	\N	1	2020-01-30 16:30:00+00
1170	1	testTask	renren	0	\N	0	2020-01-30 17:00:00+00
1171	1	testTask	renren	0	\N	1	2020-01-30 17:30:00+00
1172	1	testTask	renren	0	\N	1	2020-01-30 18:00:00+00
1173	1	testTask	renren	0	\N	1	2020-01-30 18:30:00+00
1174	1	testTask	renren	0	\N	1	2020-01-30 19:00:00+00
1175	1	testTask	renren	0	\N	1	2020-01-30 19:30:00+00
1176	1	testTask	renren	0	\N	0	2020-01-30 20:00:00+00
1177	1	testTask	renren	0	\N	1	2020-01-30 20:30:00+00
1178	1	testTask	renren	0	\N	3	2020-02-01 10:30:00+00
1179	1	testTask	renren	0	\N	1	2020-02-01 11:00:00+00
1180	1	testTask	renren	0	\N	1	2020-02-01 11:30:00+00
1181	1	testTask	renren	0	\N	1	2020-02-01 12:00:00+00
1182	1	testTask	renren	0	\N	1	2020-02-01 12:30:00+00
1183	1	testTask	renren	0	\N	0	2020-02-18 15:00:00+00
1184	1	testTask	renren	0	\N	1	2020-02-18 15:30:00+00
1185	1	testTask	renren	0	\N	10	2020-02-18 16:00:00+00
1186	1	testTask	renren	0	\N	0	2020-02-18 18:00:00+00
1187	1	testTask	renren	0	\N	1	2020-02-18 18:30:00+00
1188	1	testTask	renren	0	\N	1	2020-02-18 19:00:00+00
1189	1	testTask	renren	0	\N	1	2020-02-18 19:30:00+00
1190	1	testTask	renren	0	\N	1	2020-02-18 20:00:00+00
1191	1	testTask	renren	0	\N	0	2020-02-18 20:30:00+00
1192	1	testTask	renren	0	\N	1	2020-02-18 21:00:00+00
1193	1	testTask	renren	0	\N	1	2020-02-18 21:30:00+00
1194	1	testTask	renren	0	\N	1	2020-02-18 22:00:00+00
1195	1	testTask	renren	0	\N	1	2020-02-18 22:30:00+00
1196	1	testTask	renren	0	\N	1	2020-02-18 23:00:00+00
1197	1	testTask	renren	0	\N	1	2020-02-18 23:30:00+00
1198	1	testTask	renren	0	\N	1	2020-02-19 15:30:00+00
1199	1	testTask	renren	0	\N	1	2020-02-19 16:00:00+00
1200	1	testTask	renren	0	\N	1	2020-02-19 16:30:00+00
1201	1	testTask	renren	0	\N	1	2020-02-19 17:00:00+00
1202	1	testTask	renren	0	\N	1	2020-02-19 17:30:00+00
1203	1	testTask	renren	0	\N	1	2020-02-19 18:00:00+00
1204	1	testTask	renren	0	\N	1	2020-02-19 18:30:00+00
1205	1	testTask	renren	0	\N	1	2020-02-19 19:00:00+00
1206	1	testTask	renren	0	\N	0	2020-02-19 19:30:00+00
1207	1	testTask	renren	0	\N	1	2020-02-19 20:00:00+00
1208	1	testTask	renren	0	\N	0	2020-02-19 20:30:00+00
1209	1	testTask	renren	0	\N	1	2020-02-20 12:30:00+00
1210	1	testTask	renren	0	\N	1	2020-02-20 13:00:00+00
1211	1	testTask	renren	0	\N	1	2020-02-20 13:30:00+00
1212	1	testTask	renren	0	\N	1	2020-02-20 14:00:00+00
1213	1	testTask	renren	0	\N	1	2020-02-20 14:30:00+00
1214	1	testTask	renren	0	\N	0	2020-02-20 15:00:00+00
1215	1	testTask	renren	0	\N	0	2020-02-20 15:30:00+00
1216	1	testTask	renren	0	\N	1	2020-02-20 16:00:00+00
1217	1	testTask	renren	0	\N	1	2020-02-20 16:30:00+00
1218	1	testTask	renren	0	\N	1	2020-02-20 17:00:00+00
1219	1	testTask	renren	0	\N	1	2020-02-20 17:30:00+00
1220	1	testTask	renren	0	\N	2	2020-02-20 21:00:00+00
1221	1	testTask	renren	0	\N	1	2020-02-21 11:00:00+00
1222	1	testTask	renren	0	\N	1	2020-02-21 11:30:00+00
1223	1	testTask	renren	0	\N	1	2020-02-21 12:00:00+00
1224	1	testTask	renren	0	\N	0	2020-02-21 12:30:00+00
1225	1	testTask	renren	0	\N	1	2020-02-22 11:30:00+00
1226	1	testTask	renren	0	\N	0	2020-02-22 12:00:00+00
1227	1	testTask	renren	0	\N	0	2020-02-22 12:30:00+00
1228	1	testTask	renren	0	\N	1	2020-02-22 13:00:00+00
1229	1	testTask	renren	0	\N	1	2020-02-22 13:30:00+00
1230	1	testTask	renren	0	\N	3	2020-02-22 14:00:00+00
1231	1	testTask	renren	0	\N	2	2020-02-22 14:30:00+00
1232	1	testTask	renren	0	\N	1	2020-02-22 15:00:00+00
1233	1	testTask	renren	0	\N	1	2020-02-22 15:30:00+00
1234	1	testTask	renren	0	\N	0	2020-02-22 16:00:00+00
1235	1	testTask	renren	0	\N	4	2020-02-22 16:30:00+00
1236	1	testTask	renren	0	\N	0	2020-02-22 17:00:00+00
1237	1	testTask	renren	0	\N	1	2020-02-22 17:30:00+00
1238	1	testTask	renren	0	\N	1	2020-02-24 16:00:00+00
1239	1	testTask	renren	0	\N	1	2020-02-24 16:30:00+00
1240	1	testTask	renren	0	\N	0	2020-02-24 17:00:00+00
1241	1	testTask	renren	0	\N	0	2020-02-24 17:30:00+00
1242	1	testTask	renren	0	\N	0	2020-02-24 18:00:00+00
1243	1	testTask	renren	0	\N	1	2020-02-24 18:30:00+00
1244	1	testTask	renren	0	\N	1	2020-02-24 19:00:00+00
1245	1	testTask	renren	0	\N	1	2020-02-24 19:30:00+00
1246	1	testTask	renren	0	\N	3	2020-02-24 20:00:00+00
1247	1	testTask	renren	0	\N	1	2020-02-24 20:30:00+00
1248	1	testTask	renren	0	\N	1	2020-02-24 21:00:00+00
1249	1	testTask	renren	0	\N	0	2020-02-24 21:30:00+00
1250	1	testTask	renren	0	\N	1	2020-02-24 22:00:00+00
1251	1	testTask	renren	0	\N	1	2020-02-24 22:30:00+00
1252	1	testTask	renren	0	\N	1	2020-02-24 23:00:00+00
1253	1	testTask	renren	0	\N	1	2020-02-24 23:30:00+00
1254	1	testTask	renren	0	\N	10	2020-02-25 00:00:00+00
1255	1	testTask	renren	0	\N	0	2020-02-25 12:00:00+00
1256	1	testTask	renren	0	\N	1	2020-02-25 12:30:00+00
1257	1	testTask	renren	0	\N	1	2020-02-25 13:00:00+00
1258	1	testTask	renren	0	\N	1	2020-02-25 13:30:00+00
1259	1	testTask	renren	0	\N	0	2020-02-25 14:00:00+00
1260	1	testTask	renren	0	\N	1	2020-02-25 14:30:00+00
1261	1	testTask	renren	0	\N	1	2020-02-25 15:00:00+00
1262	1	testTask	renren	0	\N	1	2020-02-25 15:30:00+00
1263	1	testTask	renren	0	\N	1	2020-02-25 16:00:00+00
1264	1	testTask	renren	0	\N	1	2020-02-25 16:30:00+00
1265	1	testTask	renren	0	\N	1	2020-02-25 17:00:00+00
1266	1	testTask	renren	0	\N	1	2020-02-25 17:30:00+00
1267	1	testTask	renren	0	\N	2	2020-02-25 18:00:00+00
1268	1	testTask	renren	0	\N	3	2020-02-25 18:30:00+00
1269	1	testTask	renren	0	\N	1	2020-02-25 19:00:00+00
1270	1	testTask	renren	0	\N	2	2020-02-25 19:30:00+00
1271	1	testTask	renren	0	\N	1	2020-02-25 20:00:00+00
1272	1	testTask	renren	0	\N	1	2020-02-25 20:30:00+00
1273	1	testTask	renren	0	\N	1	2020-02-25 21:00:00+00
1274	1	testTask	renren	0	\N	2	2020-02-25 21:30:00+00
1275	1	testTask	renren	0	\N	3	2020-02-25 22:00:00+00
1276	1	testTask	renren	0	\N	1	2020-03-04 09:30:00+00
1277	1	testTask	renren	0	\N	1	2020-03-04 22:30:00+00
1278	1	testTask	renren	0	\N	2	2020-03-04 23:00:00+00
1279	1	testTask	renren	0	\N	1	2020-03-04 23:30:00+00
1280	1	testTask	renren	0	\N	9	2020-03-05 00:00:00+00
1281	1	testTask	renren	0	\N	2	2020-03-05 10:00:00+00
1282	1	testTask	renren	0	\N	2	2020-03-05 10:30:00+00
1283	1	testTask	renren	0	\N	1	2020-03-05 11:00:00+00
1284	1	testTask	renren	0	\N	1	2020-03-05 11:30:00+00
1285	1	testTask	renren	0	\N	3	2020-03-05 12:00:00+00
1286	1	testTask	renren	0	\N	1	2020-03-05 14:00:00+00
1287	1	testTask	renren	0	\N	1	2020-03-05 14:30:00+00
1288	1	testTask	renren	0	\N	0	2020-03-05 15:00:00+00
1289	1	testTask	renren	0	\N	0	2020-03-05 15:30:00+00
1290	1	testTask	renren	0	\N	0	2020-03-05 16:00:00+00
1291	1	testTask	renren	0	\N	1	2020-03-05 16:30:00+00
1292	1	testTask	renren	0	\N	1	2020-03-05 17:00:00+00
1293	1	testTask	renren	0	\N	1	2020-03-05 17:30:00+00
1294	1	testTask	renren	0	\N	1	2020-03-05 18:00:00+00
1295	1	testTask	renren	0	\N	4	2020-03-05 18:30:00+00
1296	1	testTask	renren	0	\N	1	2020-03-05 19:00:00+00
1297	1	testTask	renren	0	\N	4	2020-03-05 19:30:00+00
1298	1	testTask	renren	0	\N	3	2020-03-05 20:00:00+00
1299	1	testTask	renren	0	\N	1	2020-03-05 20:30:00+00
1300	1	testTask	renren	0	\N	2	2020-03-05 21:00:00+00
1301	1	testTask	renren	0	\N	1	2020-03-05 21:30:00+00
1302	1	testTask	renren	0	\N	1	2020-03-07 21:30:00+00
1303	1	testTask	renren	0	\N	10	2020-03-10 22:30:00+00
1304	1	testTask	renren	0	\N	0	2020-03-10 23:00:00+00
1305	1	testTask	renren	0	\N	1	2020-03-10 23:30:00+00
1306	1	testTask	renren	0	\N	0	2020-03-11 00:00:00+00
\.


--
-- Data for Name: sys_captcha; Type: TABLE DATA; Schema: public; Owner: faris
--

COPY public.sys_captcha (uuid, code, expire_time) FROM stdin;
05c69d0b-1ba0-41f0-8944-475ab4274986	fmb86	2020-03-07 20:47:16+00
071ccd85-732c-4812-8e9d-7c29faf8a01f	f8ge3	2019-09-30 11:38:08+00
08fa63a8-9d73-4d4e-8717-b5b89795ede5	b2cwy	2020-03-07 20:51:57+00
123eeb2b-9032-4154-87fc-ffcaadf552b6	7e33g	2019-12-19 09:57:22+00
144b1d72-d40b-480e-8533-eca4c69888c8	dfene	2020-03-07 20:52:14+00
180c1579-0cdc-4caf-8c1a-0a741680cc0b	355nd	2019-10-29 11:30:28+00
20bfa304-b049-4850-8873-91bec5406416	787nn	2019-10-29 10:23:53+00
22439312-a5d8-4af4-84bf-03c54022c453	dpnpb	2019-10-29 11:30:26+00
27a9ab10-263c-4018-8e8a-b29910250f67	4y32y	2019-09-30 11:38:00+00
2800b4e3-2911-4a56-8841-91e1c786162e	nan5c	2020-02-22 11:45:23+00
2c4ad84d-73b0-4ad3-83d2-238f40657a49	dmyp6	2019-09-30 11:38:13+00
38bf7980-41dc-478c-8bec-51476ee55d8b	fnda8	2019-10-28 23:33:57+00
40520a5f-5516-4b35-83e7-897901d0819c	cw2mf	2019-10-29 10:22:31+00
45c192b3-bb8d-4fe0-8903-1b5e0e2876ed	w53ym	2019-10-29 10:18:19+00
4fffb991-9d5c-4af3-8033-bbdb70c69976	app6d	2020-03-04 09:19:01+00
57c3a23b-3931-4cec-89fe-695c03d661e0	865y4	2020-02-21 15:39:45+00
5a0ea466-40b1-426f-8c95-406c372efafd	m2gpx	2019-10-29 12:10:55+00
6097823b-7a6d-4b9a-8aff-2f32f1202ea0	4adp3	2019-10-29 10:23:57+00
63314ba2-a03b-4118-82e7-da54f00213d9	b567x	2019-10-29 10:46:24+00
678118fa-1a9a-4bc4-8cab-d568070542e9	3ey5a	2019-10-28 23:16:36+00
6f664f22-22cb-4ede-8799-4b8cebfaa258	bp5mg	2019-10-28 23:18:39+00
6f82a9f4-221b-4486-848e-3a83f00252c7	pamw5	2020-02-22 11:45:20+00
7123ebc0-3ec3-4c73-8461-f8a240ab9858	72f5y	2019-10-29 10:56:10+00
72846ba8-afff-4f74-891e-de56255c5d37	a42an	2019-10-29 10:22:35+00
733e465e-ff41-4d8d-85a0-7265b34b711e	8pbeg	2020-03-07 20:51:43+00
7a611a55-b45f-43c4-8632-60d78055ddd8	n8fcg	2019-10-28 23:26:12+00
8a8482fa-0bab-4d8d-83f2-00050f58ddf3	6c22w	2019-10-29 10:21:59+00
8abf953f-bb4e-4a25-8515-468e58ee6554	pnp3n	2020-02-22 12:01:29+00
8fcff20f-d518-43e1-84a8-6f859cd76d3c	wxx2n	2020-03-07 21:27:14+00
96c9d01e-7cf9-4ecb-8817-a5c6030c5c8f	nxb4m	2019-10-28 23:22:26+00
a40f0707-8853-4944-8895-15276df7f4a1	mmdan	2020-03-07 20:51:28+00
a6dd10cb-3db3-4ac0-8d72-7c1244681631	cx6a2	2019-10-28 23:12:59+00
a801ecf8-1602-47e0-8a07-436b0136cc89	yygyx	2020-03-07 20:45:43+00
a94444c3-6cb0-4662-8ca6-47e7bb3c99c6	w72aa	2019-10-28 23:11:06+00
aad10794-43b6-4546-849d-d401848dd972	pnmmg	2019-09-30 11:38:14+00
af32a584-dfea-433d-8275-633d19a85b7e	ebf65	2020-03-07 20:53:10+00
bc7e0494-4f9e-4262-8b1d-e87580ae8834	e5cnp	2020-02-21 15:39:23+00
c624ad95-1349-495b-85aa-41a7b11fc9ba	fg62b	2019-10-28 21:37:30+00
c868dc0a-d6b9-45aa-8081-771ab87807ea	m3na3	2020-03-07 20:43:03+00
cd46d32b-172c-48d9-8db2-861246571e2f	6wxcf	2019-10-29 10:21:55+00
d155122f-d753-4bca-8977-f38ce6cecdb2	edgp7	2019-10-29 10:22:24+00
d7a93a64-a420-40be-8b8e-c449b53d8862	ddnew	2019-10-29 10:52:53+00
df31e84c-420d-44a2-8093-ce53c801235c	cpd5n	2019-10-29 10:19:32+00
e410a7c2-3409-4fde-8217-d824035d0f1b	cn2bg	2019-10-29 12:10:55+00
eb756ff9-5300-4969-885b-4248e58e43c9	eecxf	2020-03-07 20:51:32+00
edcb5be1-97da-4b7d-8fd5-9861f4e2653d	nfad5	2019-10-28 23:15:07+00
f6992138-55ce-4d90-8876-a9fd99287f88	d8xpp	2019-11-04 21:52:50+00
fcdc0016-ac09-4a4b-8c4c-376c1ca8c801	mw85n	2019-10-29 11:30:26+00
fee96fac-a4b8-46ab-89bb-93d3673163e8	2b4nd	2020-02-21 21:46:43+00
\.


--
-- Data for Name: sys_config; Type: TABLE DATA; Schema: public; Owner: faris
--

COPY public.sys_config (id, param_key, param_value, status, remark) FROM stdin;
1	CLOUD_STORAGE_CONFIG_KEY	{"aliyunAccessKeyId":"","aliyunAccessKeySecret":"","aliyunBucketName":"","aliyunDomain":"","aliyunEndPoint":"","aliyunPrefix":"","qcloudBucketName":"","qcloudDomain":"","qcloudPrefix":"","qcloudSecretId":"","qcloudSecretKey":"","qiniuAccessKey":"NrgMfABZxWLo5B-YYSjoE8-AZ1EISdi1Z3ubLOeZ","qiniuBucketName":"ios-app","qiniuDomain":"http://7xqbwh.dl1.z0.glb.clouddn.com","qiniuPrefix":"upload","qiniuSecretKey":"uIwJHevMRWU0VLxFvgy0tAcOdGqasdtVlJkdy6vV","type":1}	0	云存储配置信息
\.


--
-- Data for Name: sys_log; Type: TABLE DATA; Schema: public; Owner: faris
--

COPY public.sys_log (id, username, operation, method, params, "time", ip, create_date) FROM stdin;
1	admin	保存菜单	io.renren.modules.sys.controller.SysMenuController.save()	[{"menuId":31,"parentId":0,"name":"商品系统","url":"","perms":"","type":0,"icon":"editor","orderNum":0}]	9	0:0:0:0:0:0:0:1	2019-10-28 21:34:21+00
2	admin	保存菜单	io.renren.modules.sys.controller.SysMenuController.save()	[{"menuId":32,"parentId":31,"name":"分类维护","url":"product/category","perms":"","type":1,"icon":"menu","orderNum":0}]	8	0:0:0:0:0:0:0:1	2019-10-28 21:35:21+00
3	admin	保存菜单	io.renren.modules.sys.controller.SysMenuController.save()	[{"menuId":33,"parentId":31,"name":"品牌管理","url":"product/brand","perms":"","type":1,"icon":"editor","orderNum":0}]	17	0:0:0:0:0:0:0:1	2019-11-06 10:19:56+00
4	admin	删除菜单	io.renren.modules.sys.controller.SysMenuController.delete()	[33]	322	0:0:0:0:0:0:0:1	2019-11-06 10:40:06+00
5	admin	保存菜单	io.renren.modules.sys.controller.SysMenuController.save()	[{"menuId":34,"parentId":31,"name":"品牌管理","url":"product/brand","perms":"","type":1,"icon":"editor","orderNum":0}]	8	0:0:0:0:0:0:0:1	2019-11-06 10:52:28+00
6	admin	保存菜单	io.renren.modules.sys.controller.SysMenuController.save()	[{"menuId":35,"parentId":31,"name":"属性维护","url":"","perms":"","type":0,"icon":"system","orderNum":0}]	11	0:0:0:0:0:0:0:1	2019-11-13 11:59:27+00
7	admin	保存菜单	io.renren.modules.sys.controller.SysMenuController.save()	[{"menuId":36,"parentId":35,"name":"基本属性","url":"product/baseatr","perms":"","type":1,"icon":"","orderNum":0}]	8	0:0:0:0:0:0:0:1	2019-11-13 11:59:56+00
8	admin	删除菜单	io.renren.modules.sys.controller.SysMenuController.delete()	[35]	4	0:0:0:0:0:0:0:1	2019-11-13 12:08:23+00
9	admin	删除菜单	io.renren.modules.sys.controller.SysMenuController.delete()	[36]	311	0:0:0:0:0:0:0:1	2019-11-13 12:08:28+00
10	admin	删除菜单	io.renren.modules.sys.controller.SysMenuController.delete()	[35]	11	0:0:0:0:0:0:0:1	2019-11-13 12:08:34+00
11	admin	保存菜单	io.renren.modules.sys.controller.SysMenuController.save()	[{"menuId":37,"parentId":31,"name":"平台属性","url":"","perms":"","type":0,"icon":"system","orderNum":0}]	11	0:0:0:0:0:0:0:1	2019-11-13 20:05:22+00
12	admin	保存菜单	io.renren.modules.sys.controller.SysMenuController.save()	[{"menuId":38,"parentId":37,"name":"属性分组","url":"product/attrgroup","perms":"","type":1,"icon":"tubiao","orderNum":0}]	7	0:0:0:0:0:0:0:1	2019-11-13 20:06:12+00
13	admin	保存菜单	io.renren.modules.sys.controller.SysMenuController.save()	[{"menuId":39,"parentId":37,"name":"规格参数","url":"product/baseattr","perms":"","type":1,"icon":"log","orderNum":0}]	6	0:0:0:0:0:0:0:1	2019-11-13 20:07:29+00
14	admin	保存菜单	io.renren.modules.sys.controller.SysMenuController.save()	[{"menuId":40,"parentId":37,"name":"销售属性","url":"product/saleattr","perms":"","type":1,"icon":"zonghe","orderNum":0}]	8	0:0:0:0:0:0:0:1	2019-11-13 20:08:00+00
15	admin	保存菜单	io.renren.modules.sys.controller.SysMenuController.save()	[{"menuId":41,"parentId":31,"name":"商品维护","url":"product/spu","perms":"","type":1,"icon":"zonghe","orderNum":0}]	7	0:0:0:0:0:0:0:1	2019-11-13 20:13:12+00
16	admin	保存菜单	io.renren.modules.sys.controller.SysMenuController.save()	[{"menuId":42,"parentId":0,"name":"优惠营销","url":"","perms":"","type":0,"icon":"mudedi","orderNum":0}]	5	0:0:0:0:0:0:0:1	2019-11-13 20:14:52+00
17	admin	保存菜单	io.renren.modules.sys.controller.SysMenuController.save()	[{"menuId":43,"parentId":0,"name":"库存系统","url":"","perms":"","type":0,"icon":"shouye","orderNum":0}]	7	0:0:0:0:0:0:0:1	2019-11-13 20:15:20+00
18	admin	保存菜单	io.renren.modules.sys.controller.SysMenuController.save()	[{"menuId":44,"parentId":0,"name":"订单系统","url":"","perms":"","type":0,"icon":"config","orderNum":0}]	5	0:0:0:0:0:0:0:1	2019-11-13 20:15:48+00
19	admin	保存菜单	io.renren.modules.sys.controller.SysMenuController.save()	[{"menuId":45,"parentId":0,"name":"用户系统","url":"","perms":"","type":0,"icon":"admin","orderNum":0}]	5	0:0:0:0:0:0:0:1	2019-11-13 20:16:12+00
20	admin	保存菜单	io.renren.modules.sys.controller.SysMenuController.save()	[{"menuId":46,"parentId":0,"name":"内容管理","url":"","perms":"","type":0,"icon":"sousuo","orderNum":0}]	3	0:0:0:0:0:0:0:1	2019-11-13 20:16:56+00
21	admin	保存菜单	io.renren.modules.sys.controller.SysMenuController.save()	[{"menuId":47,"parentId":42,"name":"优惠券管理","url":"coupon/coupon","perms":"","type":1,"icon":"zhedie","orderNum":0}]	7	0:0:0:0:0:0:0:1	2019-11-13 20:19:59+00
22	admin	保存菜单	io.renren.modules.sys.controller.SysMenuController.save()	[{"menuId":48,"parentId":42,"name":"发放记录","url":"coupon/history","perms":"","type":1,"icon":"sql","orderNum":0}]	15	0:0:0:0:0:0:0:1	2019-11-13 20:20:52+00
23	admin	保存菜单	io.renren.modules.sys.controller.SysMenuController.save()	[{"menuId":49,"parentId":42,"name":"专题活动","url":"coupon/subject","perms":"","type":1,"icon":"tixing","orderNum":0}]	7	0:0:0:0:0:0:0:1	2019-11-13 20:21:58+00
24	admin	保存菜单	io.renren.modules.sys.controller.SysMenuController.save()	[{"menuId":50,"parentId":42,"name":"秒杀活动","url":"coupon/seckill","perms":"","type":1,"icon":"daohang","orderNum":0}]	7	0:0:0:0:0:0:0:1	2019-11-13 20:22:32+00
25	admin	保存菜单	io.renren.modules.sys.controller.SysMenuController.save()	[{"menuId":51,"parentId":42,"name":"积分维护","url":"coupon/bounds","perms":"","type":1,"icon":"geren","orderNum":0}]	5	0:0:0:0:0:0:0:1	2019-11-13 20:25:13+00
26	admin	保存菜单	io.renren.modules.sys.controller.SysMenuController.save()	[{"menuId":52,"parentId":42,"name":"满减折扣","url":"coupon/full","perms":"","type":1,"icon":"shoucang","orderNum":0}]	9	0:0:0:0:0:0:0:1	2019-11-13 20:26:21+00
27	admin	保存菜单	io.renren.modules.sys.controller.SysMenuController.save()	[{"menuId":53,"parentId":43,"name":"仓库维护","url":"ware/wareinfo","perms":"","type":1,"icon":"shouye","orderNum":0}]	5	0:0:0:0:0:0:0:1	2019-11-13 20:27:51+00
28	admin	保存菜单	io.renren.modules.sys.controller.SysMenuController.save()	[{"menuId":54,"parentId":43,"name":"库存工作单","url":"ware/task","perms":"","type":1,"icon":"log","orderNum":0}]	14	0:0:0:0:0:0:0:1	2019-11-13 20:28:55+00
29	admin	保存菜单	io.renren.modules.sys.controller.SysMenuController.save()	[{"menuId":55,"parentId":43,"name":"商品库存","url":"ware/sku","perms":"","type":1,"icon":"jiesuo","orderNum":0}]	5	0:0:0:0:0:0:0:1	2019-11-13 20:29:31+00
30	admin	保存菜单	io.renren.modules.sys.controller.SysMenuController.save()	[{"menuId":56,"parentId":44,"name":"订单查询","url":"order/order","perms":"","type":1,"icon":"zhedie","orderNum":0}]	6	0:0:0:0:0:0:0:1	2019-11-13 20:31:55+00
31	admin	保存菜单	io.renren.modules.sys.controller.SysMenuController.save()	[{"menuId":57,"parentId":44,"name":"退货单处理","url":"order/return","perms":"","type":1,"icon":"shanchu","orderNum":0}]	5	0:0:0:0:0:0:0:1	2019-11-13 20:33:04+00
32	admin	保存菜单	io.renren.modules.sys.controller.SysMenuController.save()	[{"menuId":58,"parentId":44,"name":"等级规则","url":"order/settings","perms":"","type":1,"icon":"system","orderNum":0}]	5	0:0:0:0:0:0:0:1	2019-11-13 20:34:34+00
33	admin	保存菜单	io.renren.modules.sys.controller.SysMenuController.save()	[{"menuId":59,"parentId":44,"name":"支付流水查询","url":"order/payment","perms":"","type":1,"icon":"job","orderNum":0}]	5	0:0:0:0:0:0:0:1	2019-11-13 20:37:41+00
34	admin	保存菜单	io.renren.modules.sys.controller.SysMenuController.save()	[{"menuId":60,"parentId":44,"name":"退款流水查询","url":"order/refund","perms":"","type":1,"icon":"mudedi","orderNum":0}]	6	0:0:0:0:0:0:0:1	2019-11-13 20:38:16+00
35	admin	保存菜单	io.renren.modules.sys.controller.SysMenuController.save()	[{"menuId":61,"parentId":45,"name":"会员列表","url":"member/member","perms":"","type":1,"icon":"geren","orderNum":0}]	7	0:0:0:0:0:0:0:1	2019-11-13 20:40:14+00
36	admin	保存菜单	io.renren.modules.sys.controller.SysMenuController.save()	[{"menuId":62,"parentId":45,"name":"会员等级","url":"member/level","perms":"","type":1,"icon":"tubiao","orderNum":0}]	14	0:0:0:0:0:0:0:1	2019-11-13 20:40:34+00
37	admin	保存菜单	io.renren.modules.sys.controller.SysMenuController.save()	[{"menuId":63,"parentId":45,"name":"积分变化","url":"member/growth","perms":"","type":1,"icon":"bianji","orderNum":0}]	6	0:0:0:0:0:0:0:1	2019-11-13 20:43:14+00
38	admin	保存菜单	io.renren.modules.sys.controller.SysMenuController.save()	[{"menuId":64,"parentId":45,"name":"统计信息","url":"member/statistics","perms":"","type":1,"icon":"sql","orderNum":0}]	4	0:0:0:0:0:0:0:1	2019-11-13 20:43:52+00
39	admin	保存菜单	io.renren.modules.sys.controller.SysMenuController.save()	[{"menuId":65,"parentId":46,"name":"首页推荐","url":"content/index","perms":"","type":1,"icon":"shouye","orderNum":0}]	6	0:0:0:0:0:0:0:1	2019-11-13 20:44:47+00
40	admin	保存菜单	io.renren.modules.sys.controller.SysMenuController.save()	[{"menuId":66,"parentId":46,"name":"分类热门","url":"content/category","perms":"","type":1,"icon":"zhedie","orderNum":0}]	7	0:0:0:0:0:0:0:1	2019-11-13 20:45:24+00
41	admin	保存菜单	io.renren.modules.sys.controller.SysMenuController.save()	[{"menuId":67,"parentId":46,"name":"评论管理","url":"content/comments","perms":"","type":1,"icon":"pinglun","orderNum":0}]	20	0:0:0:0:0:0:0:1	2019-11-13 20:48:21+00
42	admin	修改菜单	io.renren.modules.sys.controller.SysMenuController.update()	[{"menuId":41,"parentId":31,"name":"商品维护","url":"product/spu","perms":"","type":0,"icon":"zonghe","orderNum":0}]	11	0:0:0:0:0:0:0:1	2019-11-17 12:18:52+00
43	admin	保存菜单	io.renren.modules.sys.controller.SysMenuController.save()	[{"menuId":68,"parentId":41,"name":"spu管理","url":"product/spu","perms":"","type":1,"icon":"config","orderNum":0}]	9	0:0:0:0:0:0:0:1	2019-11-17 12:19:52+00
44	admin	保存菜单	io.renren.modules.sys.controller.SysMenuController.save()	[{"menuId":69,"parentId":41,"name":"发布商品","url":"product/spuadd","perms":"","type":1,"icon":"bianji","orderNum":0}]	14	0:0:0:0:0:0:0:1	2019-11-17 12:49:04+00
45	admin	保存菜单	io.renren.modules.sys.controller.SysMenuController.save()	[{"menuId":70,"parentId":43,"name":"采购单维护","url":"","perms":"","type":0,"icon":"tubiao","orderNum":0}]	12	0:0:0:0:0:0:0:1	2019-11-17 13:29:35+00
46	admin	保存菜单	io.renren.modules.sys.controller.SysMenuController.save()	[{"menuId":71,"parentId":70,"name":"发布采购项","url":"ware/purchaseitem","perms":"","type":1,"icon":"editor","orderNum":0}]	7	0:0:0:0:0:0:0:1	2019-11-17 13:30:23+00
47	admin	修改菜单	io.renren.modules.sys.controller.SysMenuController.update()	[{"menuId":71,"parentId":70,"name":"采购需求","url":"ware/purchaseitem","perms":"","type":1,"icon":"editor","orderNum":0}]	6	0:0:0:0:0:0:0:1	2019-11-17 13:30:57+00
48	admin	保存菜单	io.renren.modules.sys.controller.SysMenuController.save()	[{"menuId":72,"parentId":70,"name":"采购单","url":"ware/purchase","perms":"","type":1,"icon":"menu","orderNum":0}]	5	0:0:0:0:0:0:0:1	2019-11-17 13:31:32+00
49	admin	保存菜单	io.renren.modules.sys.controller.SysMenuController.save()	[{"menuId":73,"parentId":41,"name":"商品管理","url":"product/manager","perms":"","type":1,"icon":"zonghe","orderNum":0}]	8	0:0:0:0:0:0:0:1	2019-11-17 13:36:18+00
50	admin	保存菜单	io.renren.modules.sys.controller.SysMenuController.save()	[{"menuId":74,"parentId":42,"name":"会员价格","url":"coupon/memberprice","perms":"","type":1,"icon":"","orderNum":0}]	11	0:0:0:0:0:0:0:1	2019-11-24 16:23:33+00
51	admin	修改菜单	io.renren.modules.sys.controller.SysMenuController.update()	[{"menuId":74,"parentId":42,"name":"会员价格","url":"coupon/memberprice","perms":"","type":1,"icon":"admin","orderNum":0}]	9	0:0:0:0:0:0:0:1	2019-11-24 16:23:48+00
52	admin	保存用户	io.renren.modules.sys.controller.SysUserController.save()	[{"userId":2,"username":"leifengyang","password":"ed1b7fbd834332e5395d8823be934478141c3285ddccae1c55b192306571b886","salt":"M78W7WGGh2RD0QGKm86j","email":"aaaa@qq.com","mobile":"12345678912","status":1,"roleIdList":[],"createUserId":1,"createTime":"Nov 29, 2019 9:46:09 AM"}]	179	0:0:0:0:0:0:0:1	2019-11-29 09:46:09+00
53	admin	保存菜单	io.renren.modules.sys.controller.SysMenuController.save()	[{"menuId":75,"parentId":42,"name":"每日秒杀","url":"aaaaaaaa","perms":"","type":1,"icon":"job","orderNum":0}]	8	0:0:0:0:0:0:0:1	2020-02-18 18:42:37+00
54	admin	修改菜单	io.renren.modules.sys.controller.SysMenuController.update()	[{"menuId":75,"parentId":42,"name":"每日秒杀","url":"coupon/seckillsession","perms":"","type":1,"icon":"job","orderNum":0}]	11	0:0:0:0:0:0:0:1	2020-02-18 18:43:10+00
\.


--
-- Data for Name: sys_menu; Type: TABLE DATA; Schema: public; Owner: faris
--

COPY public.sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num) FROM stdin;
1	0	系统管理	\N	\N	0	system	0
2	1	管理员列表	sys/user	\N	1	admin	1
3	1	角色管理	sys/role	\N	1	role	2
4	1	菜单管理	sys/menu	\N	1	menu	3
5	1	SQL监控	http://localhost:8080/renren-fast/druid/sql.html	\N	1	sql	4
6	1	定时任务	job/schedule	\N	1	job	5
7	6	查看	\N	sys:schedule:list,sys:schedule:info	2	\N	0
8	6	新增	\N	sys:schedule:save	2	\N	0
9	6	修改	\N	sys:schedule:update	2	\N	0
10	6	删除	\N	sys:schedule:delete	2	\N	0
11	6	暂停	\N	sys:schedule:pause	2	\N	0
12	6	恢复	\N	sys:schedule:resume	2	\N	0
13	6	立即执行	\N	sys:schedule:run	2	\N	0
14	6	日志列表	\N	sys:schedule:log	2	\N	0
15	2	查看	\N	sys:user:list,sys:user:info	2	\N	0
16	2	新增	\N	sys:user:save,sys:role:select	2	\N	0
17	2	修改	\N	sys:user:update,sys:role:select	2	\N	0
18	2	删除	\N	sys:user:delete	2	\N	0
19	3	查看	\N	sys:role:list,sys:role:info	2	\N	0
20	3	新增	\N	sys:role:save,sys:menu:list	2	\N	0
21	3	修改	\N	sys:role:update,sys:menu:list	2	\N	0
22	3	删除	\N	sys:role:delete	2	\N	0
23	4	查看	\N	sys:menu:list,sys:menu:info	2	\N	0
24	4	新增	\N	sys:menu:save,sys:menu:select	2	\N	0
25	4	修改	\N	sys:menu:update,sys:menu:select	2	\N	0
26	4	删除	\N	sys:menu:delete	2	\N	0
27	1	参数管理	sys/config	sys:config:list,sys:config:info,sys:config:save,sys:config:update,sys:config:delete	1	config	6
29	1	系统日志	sys/log	sys:log:list	1	log	7
30	1	文件上传	oss/oss	sys:oss:all	1	oss	6
31	0	商品系统			0	editor	0
32	31	分类维护	product/category		1	menu	0
34	31	品牌管理	product/brand		1	editor	0
37	31	平台属性			0	system	0
38	37	属性分组	product/attrgroup		1	tubiao	0
39	37	规格参数	product/baseattr		1	log	0
40	37	销售属性	product/saleattr		1	zonghe	0
41	31	商品维护	product/spu		0	zonghe	0
42	0	优惠营销			0	mudedi	0
43	0	库存系统			0	shouye	0
44	0	订单系统			0	config	0
45	0	用户系统			0	admin	0
46	0	内容管理			0	sousuo	0
47	42	优惠券管理	coupon/coupon		1	zhedie	0
48	42	发放记录	coupon/history		1	sql	0
49	42	专题活动	coupon/subject		1	tixing	0
50	42	秒杀活动	coupon/seckill		1	daohang	0
51	42	积分维护	coupon/bounds		1	geren	0
52	42	满减折扣	coupon/full		1	shoucang	0
53	43	仓库维护	ware/wareinfo		1	shouye	0
54	43	库存工作单	ware/task		1	log	0
55	43	商品库存	ware/sku		1	jiesuo	0
56	44	订单查询	order/order		1	zhedie	0
57	44	退货单处理	order/return		1	shanchu	0
58	44	等级规则	order/settings		1	system	0
59	44	支付流水查询	order/payment		1	job	0
60	44	退款流水查询	order/refund		1	mudedi	0
61	45	会员列表	member/member		1	geren	0
62	45	会员等级	member/level		1	tubiao	0
63	45	积分变化	member/growth		1	bianji	0
64	45	统计信息	member/statistics		1	sql	0
65	46	首页推荐	content/index		1	shouye	0
66	46	分类热门	content/category		1	zhedie	0
67	46	评论管理	content/comments		1	pinglun	0
68	41	spu管理	product/spu		1	config	0
69	41	发布商品	product/spuadd		1	bianji	0
70	43	采购单维护			0	tubiao	0
71	70	采购需求	ware/purchaseitem		1	editor	0
72	70	采购单	ware/purchase		1	menu	0
73	41	商品管理	product/manager		1	zonghe	0
74	42	会员价格	coupon/memberprice		1	admin	0
75	42	每日秒杀	coupon/seckillsession		1	job	0
\.


--
-- Data for Name: sys_oss; Type: TABLE DATA; Schema: public; Owner: faris
--

COPY public.sys_oss (id, url, create_date) FROM stdin;
\.


--
-- Data for Name: sys_role; Type: TABLE DATA; Schema: public; Owner: faris
--

COPY public.sys_role (role_id, role_name, remark, create_user_id, create_time) FROM stdin;
\.


--
-- Data for Name: sys_role_menu; Type: TABLE DATA; Schema: public; Owner: faris
--

COPY public.sys_role_menu (id, role_id, menu_id) FROM stdin;
\.


--
-- Data for Name: sys_user; Type: TABLE DATA; Schema: public; Owner: faris
--

COPY public.sys_user (user_id, username, password, salt, email, mobile, status, create_user_id, create_time) FROM stdin;
1	admin	9ec9750e709431dad22365cabc5c625482e574c74adaebba7dd02f1129e4ce1d	YzcmCZNvbXocrsz9dm8e	root@renren.io	13612345678	1	1	2016-11-11 11:11:11+00
2	leifengyang	ed1b7fbd834332e5395d8823be934478141c3285ddccae1c55b192306571b886	M78W7WGGh2RD0QGKm86j	aaaa@qq.com	12345678912	1	1	2019-11-29 09:46:09+00
\.


--
-- Data for Name: sys_user_role; Type: TABLE DATA; Schema: public; Owner: faris
--

COPY public.sys_user_role (id, user_id, role_id) FROM stdin;
\.


--
-- Data for Name: sys_user_token; Type: TABLE DATA; Schema: public; Owner: faris
--

COPY public.sys_user_token (user_id, token, expire_time, update_time) FROM stdin;
1	4576ed62b3a5f3c489a17b416690c3c1	2020-03-08 09:22:45+00	2020-03-07 21:22:45+00
\.


--
-- Data for Name: tb_user; Type: TABLE DATA; Schema: public; Owner: faris
--

COPY public.tb_user (user_id, username, mobile, password, create_time) FROM stdin;
1	mark	13612345678	8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918	2017-03-23 22:37:41+00
\.


--
-- Data for Name: undo_log; Type: TABLE DATA; Schema: public; Owner: faris
--

COPY public.undo_log (id, branch_id, xid, context, rollback_info, log_status, log_created, log_modified, ext) FROM stdin;
\.


--
-- Name: schedule_job_job_id_seq; Type: SEQUENCE SET; Schema: public; Owner: faris
--

SELECT pg_catalog.setval('public.schedule_job_job_id_seq', 1, true);


--
-- Name: schedule_job_log_log_id_seq; Type: SEQUENCE SET; Schema: public; Owner: faris
--

SELECT pg_catalog.setval('public.schedule_job_log_log_id_seq', 1306, true);


--
-- Name: sys_config_id_seq; Type: SEQUENCE SET; Schema: public; Owner: faris
--

SELECT pg_catalog.setval('public.sys_config_id_seq', 1, true);


--
-- Name: sys_log_id_seq; Type: SEQUENCE SET; Schema: public; Owner: faris
--

SELECT pg_catalog.setval('public.sys_log_id_seq', 54, true);


--
-- Name: sys_menu_menu_id_seq; Type: SEQUENCE SET; Schema: public; Owner: faris
--

SELECT pg_catalog.setval('public.sys_menu_menu_id_seq', 75, true);


--
-- Name: sys_oss_id_seq; Type: SEQUENCE SET; Schema: public; Owner: faris
--

SELECT pg_catalog.setval('public.sys_oss_id_seq', 1, true);


--
-- Name: sys_role_menu_id_seq; Type: SEQUENCE SET; Schema: public; Owner: faris
--

SELECT pg_catalog.setval('public.sys_role_menu_id_seq', 1, true);


--
-- Name: sys_role_role_id_seq; Type: SEQUENCE SET; Schema: public; Owner: faris
--

SELECT pg_catalog.setval('public.sys_role_role_id_seq', 1, true);


--
-- Name: sys_user_role_id_seq; Type: SEQUENCE SET; Schema: public; Owner: faris
--

SELECT pg_catalog.setval('public.sys_user_role_id_seq', 1, true);


--
-- Name: sys_user_user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: faris
--

SELECT pg_catalog.setval('public.sys_user_user_id_seq', 2, true);


--
-- Name: tb_user_user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: faris
--

SELECT pg_catalog.setval('public.tb_user_user_id_seq', 1, true);


--
-- Name: undo_log_id_seq; Type: SEQUENCE SET; Schema: public; Owner: faris
--

SELECT pg_catalog.setval('public.undo_log_id_seq', 1, true);


--
-- Name: qrtz_blob_triggers idx_24810_primary; Type: CONSTRAINT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.qrtz_blob_triggers
    ADD CONSTRAINT idx_24810_primary PRIMARY KEY (sched_name, trigger_name, trigger_group);


--
-- Name: qrtz_calendars idx_24815_primary; Type: CONSTRAINT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.qrtz_calendars
    ADD CONSTRAINT idx_24815_primary PRIMARY KEY (sched_name, calendar_name);


--
-- Name: qrtz_cron_triggers idx_24820_primary; Type: CONSTRAINT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.qrtz_cron_triggers
    ADD CONSTRAINT idx_24820_primary PRIMARY KEY (sched_name, trigger_name, trigger_group);


--
-- Name: qrtz_fired_triggers idx_24825_primary; Type: CONSTRAINT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.qrtz_fired_triggers
    ADD CONSTRAINT idx_24825_primary PRIMARY KEY (sched_name, entry_id);


--
-- Name: qrtz_job_details idx_24830_primary; Type: CONSTRAINT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.qrtz_job_details
    ADD CONSTRAINT idx_24830_primary PRIMARY KEY (sched_name, job_name, job_group);


--
-- Name: qrtz_locks idx_24835_primary; Type: CONSTRAINT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.qrtz_locks
    ADD CONSTRAINT idx_24835_primary PRIMARY KEY (sched_name, lock_name);


--
-- Name: qrtz_paused_trigger_grps idx_24838_primary; Type: CONSTRAINT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.qrtz_paused_trigger_grps
    ADD CONSTRAINT idx_24838_primary PRIMARY KEY (sched_name, trigger_group);


--
-- Name: qrtz_scheduler_state idx_24841_primary; Type: CONSTRAINT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.qrtz_scheduler_state
    ADD CONSTRAINT idx_24841_primary PRIMARY KEY (sched_name, instance_name);


--
-- Name: qrtz_simple_triggers idx_24844_primary; Type: CONSTRAINT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.qrtz_simple_triggers
    ADD CONSTRAINT idx_24844_primary PRIMARY KEY (sched_name, trigger_name, trigger_group);


--
-- Name: qrtz_simprop_triggers idx_24849_primary; Type: CONSTRAINT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.qrtz_simprop_triggers
    ADD CONSTRAINT idx_24849_primary PRIMARY KEY (sched_name, trigger_name, trigger_group);


--
-- Name: qrtz_triggers idx_24854_primary; Type: CONSTRAINT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.qrtz_triggers
    ADD CONSTRAINT idx_24854_primary PRIMARY KEY (sched_name, trigger_name, trigger_group);


--
-- Name: schedule_job idx_24860_primary; Type: CONSTRAINT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.schedule_job
    ADD CONSTRAINT idx_24860_primary PRIMARY KEY (job_id);


--
-- Name: schedule_job_log idx_24867_primary; Type: CONSTRAINT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.schedule_job_log
    ADD CONSTRAINT idx_24867_primary PRIMARY KEY (log_id);


--
-- Name: sys_captcha idx_24873_primary; Type: CONSTRAINT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.sys_captcha
    ADD CONSTRAINT idx_24873_primary PRIMARY KEY (uuid);


--
-- Name: sys_config idx_24877_primary; Type: CONSTRAINT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.sys_config
    ADD CONSTRAINT idx_24877_primary PRIMARY KEY (id);


--
-- Name: sys_log idx_24885_primary; Type: CONSTRAINT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.sys_log
    ADD CONSTRAINT idx_24885_primary PRIMARY KEY (id);


--
-- Name: sys_menu idx_24892_primary; Type: CONSTRAINT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.sys_menu
    ADD CONSTRAINT idx_24892_primary PRIMARY KEY (menu_id);


--
-- Name: sys_oss idx_24899_primary; Type: CONSTRAINT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.sys_oss
    ADD CONSTRAINT idx_24899_primary PRIMARY KEY (id);


--
-- Name: sys_role idx_24904_primary; Type: CONSTRAINT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.sys_role
    ADD CONSTRAINT idx_24904_primary PRIMARY KEY (role_id);


--
-- Name: sys_role_menu idx_24909_primary; Type: CONSTRAINT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.sys_role_menu
    ADD CONSTRAINT idx_24909_primary PRIMARY KEY (id);


--
-- Name: sys_user idx_24914_primary; Type: CONSTRAINT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.sys_user
    ADD CONSTRAINT idx_24914_primary PRIMARY KEY (user_id);


--
-- Name: sys_user_role idx_24919_primary; Type: CONSTRAINT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.sys_user_role
    ADD CONSTRAINT idx_24919_primary PRIMARY KEY (id);


--
-- Name: sys_user_token idx_24923_primary; Type: CONSTRAINT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.sys_user_token
    ADD CONSTRAINT idx_24923_primary PRIMARY KEY (user_id);


--
-- Name: tb_user idx_24927_primary; Type: CONSTRAINT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.tb_user
    ADD CONSTRAINT idx_24927_primary PRIMARY KEY (user_id);


--
-- Name: undo_log idx_24932_primary; Type: CONSTRAINT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.undo_log
    ADD CONSTRAINT idx_24932_primary PRIMARY KEY (id);


--
-- Name: idx_24810_sched_name; Type: INDEX; Schema: public; Owner: faris
--

CREATE INDEX idx_24810_sched_name ON public.qrtz_blob_triggers USING btree (sched_name, trigger_name, trigger_group);


--
-- Name: idx_24825_idx_qrtz_ft_inst_job_req_rcvry; Type: INDEX; Schema: public; Owner: faris
--

CREATE INDEX idx_24825_idx_qrtz_ft_inst_job_req_rcvry ON public.qrtz_fired_triggers USING btree (sched_name, instance_name, requests_recovery);


--
-- Name: idx_24825_idx_qrtz_ft_j_g; Type: INDEX; Schema: public; Owner: faris
--

CREATE INDEX idx_24825_idx_qrtz_ft_j_g ON public.qrtz_fired_triggers USING btree (sched_name, job_name, job_group);


--
-- Name: idx_24825_idx_qrtz_ft_jg; Type: INDEX; Schema: public; Owner: faris
--

CREATE INDEX idx_24825_idx_qrtz_ft_jg ON public.qrtz_fired_triggers USING btree (sched_name, job_group);


--
-- Name: idx_24825_idx_qrtz_ft_t_g; Type: INDEX; Schema: public; Owner: faris
--

CREATE INDEX idx_24825_idx_qrtz_ft_t_g ON public.qrtz_fired_triggers USING btree (sched_name, trigger_name, trigger_group);


--
-- Name: idx_24825_idx_qrtz_ft_tg; Type: INDEX; Schema: public; Owner: faris
--

CREATE INDEX idx_24825_idx_qrtz_ft_tg ON public.qrtz_fired_triggers USING btree (sched_name, trigger_group);


--
-- Name: idx_24825_idx_qrtz_ft_trig_inst_name; Type: INDEX; Schema: public; Owner: faris
--

CREATE INDEX idx_24825_idx_qrtz_ft_trig_inst_name ON public.qrtz_fired_triggers USING btree (sched_name, instance_name);


--
-- Name: idx_24830_idx_qrtz_j_grp; Type: INDEX; Schema: public; Owner: faris
--

CREATE INDEX idx_24830_idx_qrtz_j_grp ON public.qrtz_job_details USING btree (sched_name, job_group);


--
-- Name: idx_24830_idx_qrtz_j_req_recovery; Type: INDEX; Schema: public; Owner: faris
--

CREATE INDEX idx_24830_idx_qrtz_j_req_recovery ON public.qrtz_job_details USING btree (sched_name, requests_recovery);


--
-- Name: idx_24854_idx_qrtz_t_c; Type: INDEX; Schema: public; Owner: faris
--

CREATE INDEX idx_24854_idx_qrtz_t_c ON public.qrtz_triggers USING btree (sched_name, calendar_name);


--
-- Name: idx_24854_idx_qrtz_t_g; Type: INDEX; Schema: public; Owner: faris
--

CREATE INDEX idx_24854_idx_qrtz_t_g ON public.qrtz_triggers USING btree (sched_name, trigger_group);


--
-- Name: idx_24854_idx_qrtz_t_j; Type: INDEX; Schema: public; Owner: faris
--

CREATE INDEX idx_24854_idx_qrtz_t_j ON public.qrtz_triggers USING btree (sched_name, job_name, job_group);


--
-- Name: idx_24854_idx_qrtz_t_jg; Type: INDEX; Schema: public; Owner: faris
--

CREATE INDEX idx_24854_idx_qrtz_t_jg ON public.qrtz_triggers USING btree (sched_name, job_group);


--
-- Name: idx_24854_idx_qrtz_t_n_g_state; Type: INDEX; Schema: public; Owner: faris
--

CREATE INDEX idx_24854_idx_qrtz_t_n_g_state ON public.qrtz_triggers USING btree (sched_name, trigger_group, trigger_state);


--
-- Name: idx_24854_idx_qrtz_t_n_state; Type: INDEX; Schema: public; Owner: faris
--

CREATE INDEX idx_24854_idx_qrtz_t_n_state ON public.qrtz_triggers USING btree (sched_name, trigger_name, trigger_group, trigger_state);


--
-- Name: idx_24854_idx_qrtz_t_next_fire_time; Type: INDEX; Schema: public; Owner: faris
--

CREATE INDEX idx_24854_idx_qrtz_t_next_fire_time ON public.qrtz_triggers USING btree (sched_name, next_fire_time);


--
-- Name: idx_24854_idx_qrtz_t_nft_misfire; Type: INDEX; Schema: public; Owner: faris
--

CREATE INDEX idx_24854_idx_qrtz_t_nft_misfire ON public.qrtz_triggers USING btree (sched_name, misfire_instr, next_fire_time);


--
-- Name: idx_24854_idx_qrtz_t_nft_st; Type: INDEX; Schema: public; Owner: faris
--

CREATE INDEX idx_24854_idx_qrtz_t_nft_st ON public.qrtz_triggers USING btree (sched_name, trigger_state, next_fire_time);


--
-- Name: idx_24854_idx_qrtz_t_nft_st_misfire; Type: INDEX; Schema: public; Owner: faris
--

CREATE INDEX idx_24854_idx_qrtz_t_nft_st_misfire ON public.qrtz_triggers USING btree (sched_name, misfire_instr, next_fire_time, trigger_state);


--
-- Name: idx_24854_idx_qrtz_t_nft_st_misfire_grp; Type: INDEX; Schema: public; Owner: faris
--

CREATE INDEX idx_24854_idx_qrtz_t_nft_st_misfire_grp ON public.qrtz_triggers USING btree (sched_name, misfire_instr, next_fire_time, trigger_group, trigger_state);


--
-- Name: idx_24854_idx_qrtz_t_state; Type: INDEX; Schema: public; Owner: faris
--

CREATE INDEX idx_24854_idx_qrtz_t_state ON public.qrtz_triggers USING btree (sched_name, trigger_state);


--
-- Name: idx_24867_job_id; Type: INDEX; Schema: public; Owner: faris
--

CREATE INDEX idx_24867_job_id ON public.schedule_job_log USING btree (job_id);


--
-- Name: idx_24877_param_key; Type: INDEX; Schema: public; Owner: faris
--

CREATE UNIQUE INDEX idx_24877_param_key ON public.sys_config USING btree (param_key);


--
-- Name: idx_24914_username; Type: INDEX; Schema: public; Owner: faris
--

CREATE UNIQUE INDEX idx_24914_username ON public.sys_user USING btree (username);


--
-- Name: idx_24923_token; Type: INDEX; Schema: public; Owner: faris
--

CREATE UNIQUE INDEX idx_24923_token ON public.sys_user_token USING btree (token);


--
-- Name: idx_24927_username; Type: INDEX; Schema: public; Owner: faris
--

CREATE UNIQUE INDEX idx_24927_username ON public.tb_user USING btree (username);


--
-- Name: idx_24932_ux_undo_log; Type: INDEX; Schema: public; Owner: faris
--

CREATE UNIQUE INDEX idx_24932_ux_undo_log ON public.undo_log USING btree (xid, branch_id);


--
-- Name: qrtz_blob_triggers qrtz_blob_triggers_ibfk_1; Type: FK CONSTRAINT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.qrtz_blob_triggers
    ADD CONSTRAINT qrtz_blob_triggers_ibfk_1 FOREIGN KEY (sched_name, trigger_name, trigger_group) REFERENCES public.qrtz_triggers(sched_name, trigger_name, trigger_group) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- Name: qrtz_cron_triggers qrtz_cron_triggers_ibfk_1; Type: FK CONSTRAINT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.qrtz_cron_triggers
    ADD CONSTRAINT qrtz_cron_triggers_ibfk_1 FOREIGN KEY (sched_name, trigger_name, trigger_group) REFERENCES public.qrtz_triggers(sched_name, trigger_name, trigger_group) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- Name: qrtz_simple_triggers qrtz_simple_triggers_ibfk_1; Type: FK CONSTRAINT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.qrtz_simple_triggers
    ADD CONSTRAINT qrtz_simple_triggers_ibfk_1 FOREIGN KEY (sched_name, trigger_name, trigger_group) REFERENCES public.qrtz_triggers(sched_name, trigger_name, trigger_group) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- Name: qrtz_simprop_triggers qrtz_simprop_triggers_ibfk_1; Type: FK CONSTRAINT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.qrtz_simprop_triggers
    ADD CONSTRAINT qrtz_simprop_triggers_ibfk_1 FOREIGN KEY (sched_name, trigger_name, trigger_group) REFERENCES public.qrtz_triggers(sched_name, trigger_name, trigger_group) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- Name: qrtz_triggers qrtz_triggers_ibfk_1; Type: FK CONSTRAINT; Schema: public; Owner: faris
--

ALTER TABLE ONLY public.qrtz_triggers
    ADD CONSTRAINT qrtz_triggers_ibfk_1 FOREIGN KEY (sched_name, job_name, job_group) REFERENCES public.qrtz_job_details(sched_name, job_name, job_group) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- PostgreSQL database dump complete
--

\unrestrict WPcUjOluzYBbe9WlZZjaJoClGyOV2xt4LqAWG52AK8uRk22DJTPMT5NYQkZotOY

