--
-- PostgreSQL database dump
--

\restrict 14sjCZtUvbGWf1ZEg5WAYTDgUPF0myo6BOdwY6yplbMa9zoUYjcynAlJ6a3fR51

-- Dumped from database version 17.10
-- Dumped by pg_dump version 17.10

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
-- Name: undo_log; Type: TABLE; Schema: public; Owner: -
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


--
-- Name: undo_log_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.undo_log_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: undo_log_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.undo_log_id_seq OWNED BY public.undo_log.id;


--
-- Name: wms_purchase; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.wms_purchase (
    id bigint NOT NULL,
    assignee_id bigint,
    assignee_name character varying(255),
    phone character(13),
    priority integer,
    status integer,
    ware_id bigint,
    amount numeric(18,4),
    create_time timestamp with time zone,
    update_time timestamp with time zone
);


--
-- Name: TABLE wms_purchase; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON TABLE public.wms_purchase IS '采购信息';


--
-- Name: wms_purchase_detail; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.wms_purchase_detail (
    id bigint NOT NULL,
    purchase_id bigint,
    sku_id bigint,
    sku_num integer,
    sku_price numeric(18,4),
    ware_id bigint,
    status integer
);


--
-- Name: COLUMN wms_purchase_detail.purchase_id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN public.wms_purchase_detail.purchase_id IS '采购单id';


--
-- Name: COLUMN wms_purchase_detail.sku_id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN public.wms_purchase_detail.sku_id IS '采购商品id';


--
-- Name: COLUMN wms_purchase_detail.sku_num; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN public.wms_purchase_detail.sku_num IS '采购数量';


--
-- Name: COLUMN wms_purchase_detail.sku_price; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN public.wms_purchase_detail.sku_price IS '采购金额';


--
-- Name: COLUMN wms_purchase_detail.ware_id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN public.wms_purchase_detail.ware_id IS '仓库id';


--
-- Name: COLUMN wms_purchase_detail.status; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN public.wms_purchase_detail.status IS '状态[0新建，1已分配，2正在采购，3已完成，4采购失败]';


--
-- Name: wms_purchase_detail_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.wms_purchase_detail_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: wms_purchase_detail_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.wms_purchase_detail_id_seq OWNED BY public.wms_purchase_detail.id;


--
-- Name: wms_purchase_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.wms_purchase_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: wms_purchase_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.wms_purchase_id_seq OWNED BY public.wms_purchase.id;


--
-- Name: wms_ware_info; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.wms_ware_info (
    id bigint NOT NULL,
    name character varying(255),
    address character varying(255),
    areacode character varying(20)
);


--
-- Name: TABLE wms_ware_info; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON TABLE public.wms_ware_info IS '仓库信息';


--
-- Name: COLUMN wms_ware_info.id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN public.wms_ware_info.id IS 'id';


--
-- Name: COLUMN wms_ware_info.name; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN public.wms_ware_info.name IS '仓库名';


--
-- Name: COLUMN wms_ware_info.address; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN public.wms_ware_info.address IS '仓库地址';


--
-- Name: COLUMN wms_ware_info.areacode; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN public.wms_ware_info.areacode IS '区域编码';


--
-- Name: wms_ware_info_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.wms_ware_info_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: wms_ware_info_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.wms_ware_info_id_seq OWNED BY public.wms_ware_info.id;


--
-- Name: wms_ware_order_task; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.wms_ware_order_task (
    id bigint NOT NULL,
    order_id bigint,
    order_sn character varying(255),
    consignee character varying(100),
    consignee_tel character(15),
    delivery_address character varying(500),
    order_comment character varying(200),
    payment_way boolean,
    task_status smallint,
    order_body character varying(255),
    tracking_no character(30),
    create_time timestamp with time zone,
    ware_id bigint,
    task_comment character varying(500)
);


--
-- Name: TABLE wms_ware_order_task; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON TABLE public.wms_ware_order_task IS '库存工作单';


--
-- Name: COLUMN wms_ware_order_task.id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN public.wms_ware_order_task.id IS 'id';


--
-- Name: COLUMN wms_ware_order_task.order_id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN public.wms_ware_order_task.order_id IS 'order_id';


--
-- Name: COLUMN wms_ware_order_task.order_sn; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN public.wms_ware_order_task.order_sn IS 'order_sn';


--
-- Name: COLUMN wms_ware_order_task.consignee; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN public.wms_ware_order_task.consignee IS '收货人';


--
-- Name: COLUMN wms_ware_order_task.consignee_tel; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN public.wms_ware_order_task.consignee_tel IS '收货人电话';


--
-- Name: COLUMN wms_ware_order_task.delivery_address; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN public.wms_ware_order_task.delivery_address IS '配送地址';


--
-- Name: COLUMN wms_ware_order_task.order_comment; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN public.wms_ware_order_task.order_comment IS '订单备注';


--
-- Name: COLUMN wms_ware_order_task.payment_way; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN public.wms_ware_order_task.payment_way IS '付款方式【 1:在线付款 2:货到付款】';


--
-- Name: COLUMN wms_ware_order_task.task_status; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN public.wms_ware_order_task.task_status IS '任务状态';


--
-- Name: COLUMN wms_ware_order_task.order_body; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN public.wms_ware_order_task.order_body IS '订单描述';


--
-- Name: COLUMN wms_ware_order_task.tracking_no; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN public.wms_ware_order_task.tracking_no IS '物流单号';


--
-- Name: COLUMN wms_ware_order_task.create_time; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN public.wms_ware_order_task.create_time IS 'create_time';


--
-- Name: COLUMN wms_ware_order_task.ware_id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN public.wms_ware_order_task.ware_id IS '仓库id';


--
-- Name: COLUMN wms_ware_order_task.task_comment; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN public.wms_ware_order_task.task_comment IS '工作单备注';


--
-- Name: wms_ware_order_task_detail; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.wms_ware_order_task_detail (
    id bigint NOT NULL,
    sku_id bigint,
    sku_name character varying(255),
    sku_num integer,
    task_id bigint,
    ware_id bigint,
    lock_status integer
);


--
-- Name: TABLE wms_ware_order_task_detail; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON TABLE public.wms_ware_order_task_detail IS '库存工作单';


--
-- Name: COLUMN wms_ware_order_task_detail.id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN public.wms_ware_order_task_detail.id IS 'id';


--
-- Name: COLUMN wms_ware_order_task_detail.sku_id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN public.wms_ware_order_task_detail.sku_id IS 'sku_id';


--
-- Name: COLUMN wms_ware_order_task_detail.sku_name; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN public.wms_ware_order_task_detail.sku_name IS 'sku_name';


--
-- Name: COLUMN wms_ware_order_task_detail.sku_num; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN public.wms_ware_order_task_detail.sku_num IS '购买个数';


--
-- Name: COLUMN wms_ware_order_task_detail.task_id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN public.wms_ware_order_task_detail.task_id IS '工作单id';


--
-- Name: COLUMN wms_ware_order_task_detail.ware_id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN public.wms_ware_order_task_detail.ware_id IS '仓库id';


--
-- Name: COLUMN wms_ware_order_task_detail.lock_status; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN public.wms_ware_order_task_detail.lock_status IS '1-已锁定  2-已解锁  3-扣减';


--
-- Name: wms_ware_order_task_detail_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.wms_ware_order_task_detail_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: wms_ware_order_task_detail_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.wms_ware_order_task_detail_id_seq OWNED BY public.wms_ware_order_task_detail.id;


--
-- Name: wms_ware_order_task_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.wms_ware_order_task_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: wms_ware_order_task_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.wms_ware_order_task_id_seq OWNED BY public.wms_ware_order_task.id;


--
-- Name: wms_ware_sku; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.wms_ware_sku (
    id bigint NOT NULL,
    sku_id bigint,
    ware_id bigint,
    stock integer,
    sku_name character varying(200),
    stock_locked integer DEFAULT 0
);


--
-- Name: TABLE wms_ware_sku; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON TABLE public.wms_ware_sku IS '商品库存';


--
-- Name: COLUMN wms_ware_sku.id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN public.wms_ware_sku.id IS 'id';


--
-- Name: COLUMN wms_ware_sku.sku_id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN public.wms_ware_sku.sku_id IS 'sku_id';


--
-- Name: COLUMN wms_ware_sku.ware_id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN public.wms_ware_sku.ware_id IS '仓库id';


--
-- Name: COLUMN wms_ware_sku.stock; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN public.wms_ware_sku.stock IS '库存数';


--
-- Name: COLUMN wms_ware_sku.sku_name; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN public.wms_ware_sku.sku_name IS 'sku_name';


--
-- Name: COLUMN wms_ware_sku.stock_locked; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN public.wms_ware_sku.stock_locked IS '锁定库存';


--
-- Name: wms_ware_sku_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.wms_ware_sku_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: wms_ware_sku_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.wms_ware_sku_id_seq OWNED BY public.wms_ware_sku.id;


--
-- Name: undo_log id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.undo_log ALTER COLUMN id SET DEFAULT nextval('public.undo_log_id_seq'::regclass);


--
-- Name: wms_purchase id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.wms_purchase ALTER COLUMN id SET DEFAULT nextval('public.wms_purchase_id_seq'::regclass);


--
-- Name: wms_purchase_detail id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.wms_purchase_detail ALTER COLUMN id SET DEFAULT nextval('public.wms_purchase_detail_id_seq'::regclass);


--
-- Name: wms_ware_info id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.wms_ware_info ALTER COLUMN id SET DEFAULT nextval('public.wms_ware_info_id_seq'::regclass);


--
-- Name: wms_ware_order_task id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.wms_ware_order_task ALTER COLUMN id SET DEFAULT nextval('public.wms_ware_order_task_id_seq'::regclass);


--
-- Name: wms_ware_order_task_detail id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.wms_ware_order_task_detail ALTER COLUMN id SET DEFAULT nextval('public.wms_ware_order_task_detail_id_seq'::regclass);


--
-- Name: wms_ware_sku id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.wms_ware_sku ALTER COLUMN id SET DEFAULT nextval('public.wms_ware_sku_id_seq'::regclass);


--
-- Data for Name: undo_log; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.undo_log (id, branch_id, xid, context, rollback_info, log_status, log_created, log_modified, ext) FROM stdin;
\.


--
-- Data for Name: wms_purchase; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.wms_purchase (id, assignee_id, assignee_name, phone, priority, status, ware_id, amount, create_time, update_time) FROM stdin;
\.


--
-- Data for Name: wms_purchase_detail; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.wms_purchase_detail (id, purchase_id, sku_id, sku_num, sku_price, ware_id, status) FROM stdin;
\.


--
-- Data for Name: wms_ware_info; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.wms_ware_info (id, name, address, areacode) FROM stdin;
\.


--
-- Data for Name: wms_ware_order_task; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.wms_ware_order_task (id, order_id, order_sn, consignee, consignee_tel, delivery_address, order_comment, payment_way, task_status, order_body, tracking_no, create_time, ware_id, task_comment) FROM stdin;
\.


--
-- Data for Name: wms_ware_order_task_detail; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.wms_ware_order_task_detail (id, sku_id, sku_name, sku_num, task_id, ware_id, lock_status) FROM stdin;
\.


--
-- Data for Name: wms_ware_sku; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.wms_ware_sku (id, sku_id, ware_id, stock, sku_name, stock_locked) FROM stdin;
\.


--
-- Name: undo_log_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.undo_log_id_seq', 1, true);


--
-- Name: wms_purchase_detail_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.wms_purchase_detail_id_seq', 1, true);


--
-- Name: wms_purchase_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.wms_purchase_id_seq', 1, true);


--
-- Name: wms_ware_info_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.wms_ware_info_id_seq', 1, true);


--
-- Name: wms_ware_order_task_detail_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.wms_ware_order_task_detail_id_seq', 1, true);


--
-- Name: wms_ware_order_task_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.wms_ware_order_task_id_seq', 1, true);


--
-- Name: wms_ware_sku_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.wms_ware_sku_id_seq', 1, true);


--
-- Name: undo_log idx_25271_primary; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.undo_log
    ADD CONSTRAINT idx_25271_primary PRIMARY KEY (id);


--
-- Name: wms_purchase idx_25278_primary; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.wms_purchase
    ADD CONSTRAINT idx_25278_primary PRIMARY KEY (id);


--
-- Name: wms_purchase_detail idx_25283_primary; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.wms_purchase_detail
    ADD CONSTRAINT idx_25283_primary PRIMARY KEY (id);


--
-- Name: wms_ware_info idx_25288_primary; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.wms_ware_info
    ADD CONSTRAINT idx_25288_primary PRIMARY KEY (id);


--
-- Name: wms_ware_order_task idx_25295_primary; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.wms_ware_order_task
    ADD CONSTRAINT idx_25295_primary PRIMARY KEY (id);


--
-- Name: wms_ware_order_task_detail idx_25302_primary; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.wms_ware_order_task_detail
    ADD CONSTRAINT idx_25302_primary PRIMARY KEY (id);


--
-- Name: wms_ware_sku idx_25307_primary; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.wms_ware_sku
    ADD CONSTRAINT idx_25307_primary PRIMARY KEY (id);


--
-- Name: idx_25271_ux_undo_log; Type: INDEX; Schema: public; Owner: -
--

CREATE UNIQUE INDEX idx_25271_ux_undo_log ON public.undo_log USING btree (xid, branch_id);


--
-- Name: idx_25307_sku_id; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_25307_sku_id ON public.wms_ware_sku USING btree (sku_id);


--
-- Name: idx_25307_ware_id; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_25307_ware_id ON public.wms_ware_sku USING btree (ware_id);


--
-- PostgreSQL database dump complete
--

\unrestrict 14sjCZtUvbGWf1ZEg5WAYTDgUPF0myo6BOdwY6yplbMa9zoUYjcynAlJ6a3fR51

