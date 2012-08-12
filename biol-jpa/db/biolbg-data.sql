--
-- PostgreSQL database dump
--

-- Started on 2012-05-01 14:35:17

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

--
-- TOC entry 329 (class 2612 OID 16386)
-- Name: plpgsql; Type: PROCEDURAL LANGUAGE; Schema: -; Owner: postgres
--

CREATE PROCEDURAL LANGUAGE plpgsql;


ALTER PROCEDURAL LANGUAGE plpgsql OWNER TO postgres;

SET search_path = public, pg_catalog;

--
-- TOC entry 1516 (class 1259 OID 16396)
-- Dependencies: 6
-- Name: grp_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE grp_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.grp_id_seq OWNER TO postgres;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 1517 (class 1259 OID 16398)
-- Dependencies: 1810 6
-- Name: grp; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE grp (
    id integer DEFAULT nextval('grp_id_seq'::regclass) NOT NULL,
    namebg character varying(50) NOT NULL,
    nameen character varying(50) NOT NULL,
    version integer NOT NULL
);


ALTER TABLE public.grp OWNER TO postgres;

--
-- TOC entry 1518 (class 1259 OID 16402)
-- Dependencies: 6
-- Name: homeinfo_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE homeinfo_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.homeinfo_id_seq OWNER TO postgres;

--
-- TOC entry 1519 (class 1259 OID 16404)
-- Dependencies: 1811 6
-- Name: homeinfo; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE homeinfo (
    id integer DEFAULT nextval('homeinfo_id_seq'::regclass) NOT NULL,
    headertextbg character varying(50) NOT NULL,
    infotextbg character varying(1024) NOT NULL,
    headertexten character varying(50) NOT NULL,
    infotexten character varying(1024) NOT NULL,
    version integer NOT NULL
);


ALTER TABLE public.homeinfo OWNER TO postgres;

--
-- TOC entry 1520 (class 1259 OID 16411)
-- Dependencies: 6
-- Name: item_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE item_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.item_id_seq OWNER TO postgres;

--
-- TOC entry 1521 (class 1259 OID 16413)
-- Dependencies: 1812 1813 1814 1815 1816 6
-- Name: item; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE item (
    id integer DEFAULT nextval('item_id_seq'::regclass) NOT NULL,
    group_id integer NOT NULL,
    namebg character varying(50) NOT NULL,
    nameen character varying(50) NOT NULL,
    descriptionbg character varying(1024),
    descriptionen character varying(1024),
    packingbg character varying(50) NOT NULL,
    packingen character varying(50) NOT NULL,
    measureunitbg character varying(20) NOT NULL,
    measureuniten character varying(20) NOT NULL,
    amountinpacking double precision NOT NULL,
    priceforpacking double precision NOT NULL,
    promotion smallint DEFAULT 0 NOT NULL,
    producer_id integer DEFAULT 1 NOT NULL,
    newitem smallint DEFAULT 0 NOT NULL,
    bestsell smallint DEFAULT 0 NOT NULL,
    version integer NOT NULL
);


ALTER TABLE public.item OWNER TO postgres;

--
-- TOC entry 1522 (class 1259 OID 16424)
-- Dependencies: 6
-- Name: message_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE message_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.message_id_seq OWNER TO postgres;

--
-- TOC entry 1523 (class 1259 OID 16426)
-- Dependencies: 6
-- Name: order_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE order_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.order_id_seq OWNER TO postgres;

--
-- TOC entry 1524 (class 1259 OID 16428)
-- Dependencies: 1817 1818 6
-- Name: orderm; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE orderm (
    id integer DEFAULT nextval('order_id_seq'::regclass) NOT NULL,
    postdate date NOT NULL,
    posttime time with time zone NOT NULL,
    fordate date,
    fortime time with time zone,
    usr_id integer NOT NULL,
    orderstatus_id integer NOT NULL,
    deliveryaddress character varying(50) NOT NULL,
    version integer NOT NULL,
    seenbyadmin smallint DEFAULT 0
);


ALTER TABLE public.orderm OWNER TO postgres;

--
-- TOC entry 1525 (class 1259 OID 16433)
-- Dependencies: 6
-- Name: orderrow_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE orderrow_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.orderrow_id_seq OWNER TO postgres;

--
-- TOC entry 1526 (class 1259 OID 16435)
-- Dependencies: 1819 6
-- Name: orderrow; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE orderrow (
    id integer DEFAULT nextval('orderrow_id_seq'::regclass) NOT NULL,
    order_id integer NOT NULL,
    item_id integer NOT NULL,
    amount double precision NOT NULL,
    price double precision NOT NULL
);


ALTER TABLE public.orderrow OWNER TO postgres;

--
-- TOC entry 1527 (class 1259 OID 16439)
-- Dependencies: 6
-- Name: orderstatus_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE orderstatus_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.orderstatus_id_seq OWNER TO postgres;

--
-- TOC entry 1528 (class 1259 OID 16441)
-- Dependencies: 1820 6
-- Name: orderstatus; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE orderstatus (
    id integer DEFAULT nextval('orderstatus_id_seq'::regclass) NOT NULL,
    namebg character varying(30) NOT NULL,
    nameen character varying(30) NOT NULL
);


ALTER TABLE public.orderstatus OWNER TO postgres;

--
-- TOC entry 1529 (class 1259 OID 16445)
-- Dependencies: 6
-- Name: producer_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE producer_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.producer_id_seq OWNER TO postgres;

--
-- TOC entry 1530 (class 1259 OID 16447)
-- Dependencies: 1821 6
-- Name: producer; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE producer (
    id integer DEFAULT nextval('producer_id_seq'::regclass) NOT NULL,
    namebg character varying(50) NOT NULL,
    nameen character varying(50) NOT NULL,
    version integer NOT NULL
);


ALTER TABLE public.producer OWNER TO postgres;

--
-- TOC entry 1531 (class 1259 OID 16451)
-- Dependencies: 6
-- Name: usr_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE usr_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.usr_id_seq OWNER TO postgres;

--
-- TOC entry 1532 (class 1259 OID 16453)
-- Dependencies: 1822 1823 6
-- Name: usr; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE usr (
    id integer DEFAULT nextval('usr_id_seq'::regclass) NOT NULL,
    username character varying(30) NOT NULL,
    password character varying(30) NOT NULL,
    fullname character varying(100),
    organisation character varying(100) NOT NULL,
    email character varying(100) NOT NULL,
    lastlogindate date,
    lastlogintime time with time zone,
    lastloginip character varying(30),
    adminflag smallint DEFAULT 0 NOT NULL,
    version integer NOT NULL
);


ALTER TABLE public.usr OWNER TO postgres;

--
-- TOC entry 1826 (class 2606 OID 16459)
-- Dependencies: 1517 1517
-- Name: group_namebg_unique; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY grp
    ADD CONSTRAINT group_namebg_unique UNIQUE (namebg);


--
-- TOC entry 1829 (class 2606 OID 16461)
-- Dependencies: 1517 1517
-- Name: group_nameen_unique; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY grp
    ADD CONSTRAINT group_nameen_unique UNIQUE (nameen);


--
-- TOC entry 1831 (class 2606 OID 16463)
-- Dependencies: 1517 1517
-- Name: group_pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY grp
    ADD CONSTRAINT group_pk PRIMARY KEY (id);


--
-- TOC entry 1834 (class 2606 OID 16465)
-- Dependencies: 1519 1519
-- Name: homeinfo_headertextbg_unique; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY homeinfo
    ADD CONSTRAINT homeinfo_headertextbg_unique UNIQUE (headertextbg);


--
-- TOC entry 1837 (class 2606 OID 16467)
-- Dependencies: 1519 1519
-- Name: homeinfo_headertexten_unique; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY homeinfo
    ADD CONSTRAINT homeinfo_headertexten_unique UNIQUE (headertexten);


--
-- TOC entry 1841 (class 2606 OID 16469)
-- Dependencies: 1519 1519
-- Name: homeinfo_pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY homeinfo
    ADD CONSTRAINT homeinfo_pk PRIMARY KEY (id);


--
-- TOC entry 1851 (class 2606 OID 16471)
-- Dependencies: 1521 1521
-- Name: item_namebg_unique; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY item
    ADD CONSTRAINT item_namebg_unique UNIQUE (namebg);


--
-- TOC entry 1854 (class 2606 OID 16473)
-- Dependencies: 1521 1521
-- Name: item_nameen_unique; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY item
    ADD CONSTRAINT item_nameen_unique UNIQUE (nameen);


--
-- TOC entry 1858 (class 2606 OID 16475)
-- Dependencies: 1521 1521
-- Name: item_pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY item
    ADD CONSTRAINT item_pk PRIMARY KEY (id);


--
-- TOC entry 1864 (class 2606 OID 16477)
-- Dependencies: 1524 1524
-- Name: order_pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY orderm
    ADD CONSTRAINT order_pk PRIMARY KEY (id);


--
-- TOC entry 1874 (class 2606 OID 16479)
-- Dependencies: 1526 1526
-- Name: orderrow_pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY orderrow
    ADD CONSTRAINT orderrow_pk PRIMARY KEY (id);


--
-- TOC entry 1877 (class 2606 OID 16481)
-- Dependencies: 1528 1528
-- Name: orderstatus_pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY orderstatus
    ADD CONSTRAINT orderstatus_pk PRIMARY KEY (id);


--
-- TOC entry 1880 (class 2606 OID 16483)
-- Dependencies: 1530 1530
-- Name: producer_namebg_unique; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY producer
    ADD CONSTRAINT producer_namebg_unique UNIQUE (namebg);


--
-- TOC entry 1883 (class 2606 OID 16485)
-- Dependencies: 1530 1530
-- Name: producer_nameen_unique; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY producer
    ADD CONSTRAINT producer_nameen_unique UNIQUE (nameen);


--
-- TOC entry 1885 (class 2606 OID 16487)
-- Dependencies: 1530 1530
-- Name: producer_pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY producer
    ADD CONSTRAINT producer_pk PRIMARY KEY (id);


--
-- TOC entry 1895 (class 2606 OID 16489)
-- Dependencies: 1532 1532
-- Name: usr_pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY usr
    ADD CONSTRAINT usr_pk PRIMARY KEY (id);


--
-- TOC entry 1898 (class 2606 OID 16491)
-- Dependencies: 1532 1532
-- Name: usr_username_unique; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY usr
    ADD CONSTRAINT usr_username_unique UNIQUE (username);


--
-- TOC entry 1842 (class 1259 OID 16492)
-- Dependencies: 1521
-- Name: fki_item_group_fk; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX fki_item_group_fk ON item USING btree (group_id);


--
-- TOC entry 1843 (class 1259 OID 16493)
-- Dependencies: 1521
-- Name: fki_item_producer_fk; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX fki_item_producer_fk ON item USING btree (producer_id);


--
-- TOC entry 1861 (class 1259 OID 16494)
-- Dependencies: 1524
-- Name: fki_order_orderstatus; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX fki_order_orderstatus ON orderm USING btree (orderstatus_id);


--
-- TOC entry 1862 (class 1259 OID 16495)
-- Dependencies: 1524
-- Name: fki_order_usr_fk; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX fki_order_usr_fk ON orderm USING btree (usr_id);


--
-- TOC entry 1870 (class 1259 OID 16496)
-- Dependencies: 1526
-- Name: fki_orderrow_item_fk; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX fki_orderrow_item_fk ON orderrow USING btree (item_id);


--
-- TOC entry 1871 (class 1259 OID 16497)
-- Dependencies: 1526
-- Name: fki_orderrow_order_fk; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX fki_orderrow_order_fk ON orderrow USING btree (order_id);


--
-- TOC entry 1824 (class 1259 OID 16498)
-- Dependencies: 1517
-- Name: group_namebg_index; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX group_namebg_index ON grp USING btree (namebg);


--
-- TOC entry 1827 (class 1259 OID 16499)
-- Dependencies: 1517
-- Name: group_nameen_index; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX group_nameen_index ON grp USING btree (nameen);


--
-- TOC entry 1832 (class 1259 OID 16500)
-- Dependencies: 1519
-- Name: homeinfo_headertextbg_index; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX homeinfo_headertextbg_index ON homeinfo USING btree (headertextbg);


--
-- TOC entry 1835 (class 1259 OID 16501)
-- Dependencies: 1519
-- Name: homeinfo_headertexten_index; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX homeinfo_headertexten_index ON homeinfo USING btree (headertexten);


--
-- TOC entry 1838 (class 1259 OID 16502)
-- Dependencies: 1519
-- Name: homeinfo_infotextbg_index; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX homeinfo_infotextbg_index ON homeinfo USING btree (infotextbg);


--
-- TOC entry 1839 (class 1259 OID 16503)
-- Dependencies: 1519
-- Name: homeinfo_infotexten_index; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX homeinfo_infotexten_index ON homeinfo USING btree (infotexten);


--
-- TOC entry 1844 (class 1259 OID 16504)
-- Dependencies: 1521
-- Name: item_amountinpack_index; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX item_amountinpack_index ON item USING btree (amountinpacking);


--
-- TOC entry 1845 (class 1259 OID 16505)
-- Dependencies: 1521
-- Name: item_descrbg_index; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX item_descrbg_index ON item USING btree (descriptionbg);


--
-- TOC entry 1846 (class 1259 OID 16506)
-- Dependencies: 1521
-- Name: item_descren_index; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX item_descren_index ON item USING btree (descriptionen);


--
-- TOC entry 1847 (class 1259 OID 16507)
-- Dependencies: 1521
-- Name: item_measunbg_index; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX item_measunbg_index ON item USING btree (measureunitbg);


--
-- TOC entry 1848 (class 1259 OID 16508)
-- Dependencies: 1521
-- Name: item_measunen_index; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX item_measunen_index ON item USING btree (measureuniten);


--
-- TOC entry 1849 (class 1259 OID 16509)
-- Dependencies: 1521
-- Name: item_namebg_index; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX item_namebg_index ON item USING btree (namebg);


--
-- TOC entry 1852 (class 1259 OID 16510)
-- Dependencies: 1521
-- Name: item_nameen_index; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX item_nameen_index ON item USING btree (nameen);


--
-- TOC entry 1855 (class 1259 OID 16511)
-- Dependencies: 1521
-- Name: item_packbg_index; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX item_packbg_index ON item USING btree (packingbg);


--
-- TOC entry 1856 (class 1259 OID 16512)
-- Dependencies: 1521
-- Name: item_packen_index; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX item_packen_index ON item USING btree (packingen);


--
-- TOC entry 1859 (class 1259 OID 16513)
-- Dependencies: 1521
-- Name: item_priceforpack_index; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX item_priceforpack_index ON item USING btree (priceforpacking);


--
-- TOC entry 1860 (class 1259 OID 16514)
-- Dependencies: 1521
-- Name: item_promotion_index; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX item_promotion_index ON item USING btree (promotion);


--
-- TOC entry 1865 (class 1259 OID 16515)
-- Dependencies: 1524
-- Name: orderm_delivaddr_index; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX orderm_delivaddr_index ON orderm USING btree (deliveryaddress);


--
-- TOC entry 1866 (class 1259 OID 16516)
-- Dependencies: 1524
-- Name: orderm_fordate_index; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX orderm_fordate_index ON orderm USING btree (fordate);


--
-- TOC entry 1867 (class 1259 OID 16517)
-- Dependencies: 1524
-- Name: orderm_fortime_index; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX orderm_fortime_index ON orderm USING btree (fortime);


--
-- TOC entry 1868 (class 1259 OID 16518)
-- Dependencies: 1524
-- Name: orderm_postdate_index; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX orderm_postdate_index ON orderm USING btree (postdate);


--
-- TOC entry 1869 (class 1259 OID 16519)
-- Dependencies: 1524
-- Name: orderm_posttime_index; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX orderm_posttime_index ON orderm USING btree (posttime);


--
-- TOC entry 1872 (class 1259 OID 16520)
-- Dependencies: 1526
-- Name: orderrow_amount_index; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX orderrow_amount_index ON orderrow USING btree (amount);


--
-- TOC entry 1875 (class 1259 OID 16521)
-- Dependencies: 1526
-- Name: orderrow_price_index; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX orderrow_price_index ON orderrow USING btree (price);


--
-- TOC entry 1878 (class 1259 OID 16522)
-- Dependencies: 1530
-- Name: producer_namebg_index; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX producer_namebg_index ON producer USING btree (namebg);


--
-- TOC entry 1881 (class 1259 OID 16523)
-- Dependencies: 1530
-- Name: producer_nameen_index; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX producer_nameen_index ON producer USING btree (nameen);


--
-- TOC entry 1886 (class 1259 OID 16524)
-- Dependencies: 1532
-- Name: usr_adminflag_index; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX usr_adminflag_index ON usr USING btree (adminflag);


--
-- TOC entry 1887 (class 1259 OID 16525)
-- Dependencies: 1532
-- Name: usr_email_index; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX usr_email_index ON usr USING btree (email);


--
-- TOC entry 1888 (class 1259 OID 16526)
-- Dependencies: 1532
-- Name: usr_fullname_index; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX usr_fullname_index ON usr USING btree (fullname);


--
-- TOC entry 1889 (class 1259 OID 16527)
-- Dependencies: 1532
-- Name: usr_lastlogindate_index; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX usr_lastlogindate_index ON usr USING btree (lastlogindate);


--
-- TOC entry 1890 (class 1259 OID 16528)
-- Dependencies: 1532
-- Name: usr_lastloginip_index; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX usr_lastloginip_index ON usr USING btree (lastloginip);


--
-- TOC entry 1891 (class 1259 OID 16529)
-- Dependencies: 1532
-- Name: usr_lastlogintime_index; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX usr_lastlogintime_index ON usr USING btree (lastlogintime);


--
-- TOC entry 1892 (class 1259 OID 16530)
-- Dependencies: 1532
-- Name: usr_organisation_index; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX usr_organisation_index ON usr USING btree (organisation);


--
-- TOC entry 1893 (class 1259 OID 16531)
-- Dependencies: 1532
-- Name: usr_password_index; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX usr_password_index ON usr USING btree (password);


--
-- TOC entry 1896 (class 1259 OID 16532)
-- Dependencies: 1532
-- Name: usr_username_index; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX usr_username_index ON usr USING btree (username);


--
-- TOC entry 1899 (class 2606 OID 16533)
-- Dependencies: 1517 1830 1521
-- Name: item_group_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY item
    ADD CONSTRAINT item_group_fk FOREIGN KEY (group_id) REFERENCES grp(id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 1900 (class 2606 OID 16538)
-- Dependencies: 1530 1884 1521
-- Name: item_producer_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY item
    ADD CONSTRAINT item_producer_fk FOREIGN KEY (producer_id) REFERENCES producer(id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 1901 (class 2606 OID 16543)
-- Dependencies: 1528 1524 1876
-- Name: order_orderstatus_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY orderm
    ADD CONSTRAINT order_orderstatus_fk FOREIGN KEY (orderstatus_id) REFERENCES orderstatus(id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 1902 (class 2606 OID 16548)
-- Dependencies: 1524 1894 1532
-- Name: order_usr_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY orderm
    ADD CONSTRAINT order_usr_fk FOREIGN KEY (usr_id) REFERENCES usr(id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 1903 (class 2606 OID 16553)
-- Dependencies: 1521 1526 1857
-- Name: orderrow_item_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY orderrow
    ADD CONSTRAINT orderrow_item_fk FOREIGN KEY (item_id) REFERENCES item(id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 1904 (class 2606 OID 16558)
-- Dependencies: 1524 1863 1526
-- Name: orderrow_order_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY orderrow
    ADD CONSTRAINT orderrow_order_fk FOREIGN KEY (order_id) REFERENCES orderm(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 1909 (class 0 OID 0)
-- Dependencies: 6
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2012-05-01 14:35:18

--
-- PostgreSQL database dump complete
--

