-- ProcurementOrderServiceTest 测试数据
INSERT INTO `procurement_order` (`id`, `name`, `type`, `code`, `supplier`, `procurement_department`, `procurement_date`, `delivery_date`, `plan_status`, `approval_status`, `sort`, `create_time`, `create_user`, `modify_time`, `modify_user`, `remark`, `is_enable`) VALUES (1, '', '标准采购订单', 'A2019120323471954738', '贵州黔东南旺仔集团', '采购部门', '2019-12-03', '2019-12-03', '1', '1', 1, '2019-12-03 15:56:55', '采购部大哥', '2019-12-03 15:56:55', '采购部大哥', '', 1);
INSERT INTO `procurement_order` (`id`, `name`, `type`, `code`, `supplier`, `procurement_department`, `procurement_date`, `delivery_date`, `plan_status`, `approval_status`, `sort`, `create_time`, `create_user`, `modify_time`, `modify_user`, `remark`, `is_enable`) VALUES (2, '', '标准采购订单', 'A2019120323471954738', '贵州黔东南旺仔集团', '采购部门', '2019-12-03', '2019-12-03', '0', '0', 1, '2019-12-03 15:56:55', '采购部大哥', '2019-12-03 15:56:55', '采购部大哥', '', 1);
INSERT INTO `procurement_order` (`id`, `name`, `type`, `code`, `supplier`, `procurement_department`, `procurement_date`, `delivery_date`, `plan_status`, `approval_status`, `sort`, `create_time`, `create_user`, `modify_time`, `modify_user`, `remark`, `is_enable`) VALUES (3, '', '标准采购订单', 'A2019120323471954738', '贵州黔东南旺仔集团', '采购部门', '2019-12-03', '2019-12-03', '0', '0', 1, '2019-12-03 15:56:55', '采购部大哥', '2019-12-03 15:56:55', '采购部大哥', '', 1);
INSERT INTO `procurement_order` (`id`, `name`, `type`, `code`, `supplier`, `procurement_department`, `procurement_date`, `delivery_date`, `plan_status`, `approval_status`, `sort`, `create_time`, `create_user`, `modify_time`, `modify_user`, `remark`, `is_enable`) VALUES (4, '', '标准采购订单', 'A2019120323471954738', '贵州黔东南旺仔集团', '采购部门', '2019-12-03', '2019-12-03', '0', '1', 1, '2019-12-03 15:56:55', '采购部大哥', '2019-12-03 15:56:55', '采购部大哥', '', 1);
INSERT INTO `procurement_order` (`id`, `name`, `type`, `code`, `supplier`, `procurement_department`, `procurement_date`, `delivery_date`, `plan_status`, `approval_status`, `sort`, `create_time`, `create_user`, `modify_time`, `modify_user`, `remark`, `is_enable`) VALUES (5, '', '标准采购订单', 'A2019120323471954738', '贵州黔东南旺仔集团', '采购部门', '2019-12-03', '2019-12-03', '0', '2', 1, '2019-12-03 15:56:55', '采购部大哥', '2019-12-03 15:56:55', '采购部大哥', '', 1);
INSERT INTO `procurement_order` (`id`, `name`, `type`, `code`, `supplier`, `procurement_department`, `procurement_date`, `delivery_date`, `plan_status`, `approval_status`, `sort`, `create_time`, `create_user`, `modify_time`, `modify_user`, `remark`, `is_enable`) VALUES (6, '', '标准采购订单', 'A2019120323471954738', '贵州黔东南旺仔集团', '采购部门', '2019-12-03', '2019-12-03', '6', '0', 1, '2019-12-03 15:56:55', '采购部大哥', '2019-12-03 15:56:55', '采购部大哥', '', 1);
INSERT INTO `procurement_order` (`id`, `name`, `type`, `code`, `supplier`, `procurement_department`, `procurement_date`, `delivery_date`, `plan_status`, `approval_status`, `sort`, `create_time`, `create_user`, `modify_time`, `modify_user`, `remark`, `is_enable`) VALUES (7, '', '标准采购订单', 'A2019120323471954738', '贵州黔东南旺仔集团', '采购部门', '2019-12-03', '2019-12-03', '0', '0', 1, '2019-12-03 15:56:55', '采购部大哥', '2019-12-03 15:56:55', '采购部大哥', '', 1);
INSERT INTO `plan_material` (id, name, plan_id, summary_id, procurement_plan_id, material_type_id, material_id, number, date, is_source_goods, expectation_supplier, fixed_supplier, inventory, material_tracking_code, supply_mode, supply_number, purchase_date, plan_source, status, is_use_order, sort, create_time, create_user, modify_time, modify_user, remark, is_enable) VALUES (1, null, 1, 1, null, 2, 3, 50, '20191113', 1, '珠海港口供应', '珠海港口供应', '珠海码头', '405fd5cc-74cd-4d41-a4c3-3d87164a3e7c', null, null, null, '物资需求', 0, 0, 1, '2019-11-18 16:58:18', 'japoul@163.com', '2019-11-18 16:58:18', 'japoul@163.com', null, 1);
INSERT INTO `procurement_material` (id, name, order_id, material_id, procurement_unit, procurement_number, supplier, charge_unit, charge_number, delivery_date, unit_price, taxable_unit_price, tax_rate, tax_amount, total_price, tax_total_price, is_complimentary, demand_department, material_receiving_department, plan_material_id, status, sort, create_time, create_user, modify_time, modify_user, remark, is_enable) VALUES (1, '扳手', 10000, 3, '个', 2, null, '', null, '2019-12-03', 10.00, 10.10, '1', 0.20, 20.00, 20.20, 0, '财务部门', '采购部门', 136, '', 1, '2019-12-03 15:56:55', '采购部大哥', '2019-12-03 15:56:55', '采购部大哥', null, 1);
INSERT INTO `procurement_material` (id, name, order_id, material_id, procurement_unit, procurement_number, supplier, charge_unit, charge_number, delivery_date, unit_price, taxable_unit_price, tax_rate, tax_amount, total_price, tax_total_price, is_complimentary, demand_department, material_receiving_department, plan_material_id, status, sort, create_time, create_user, modify_time, modify_user, remark, is_enable) VALUES (2, '扳手', 3, 3, '个', 4, null, '', null, '2019-12-01', 10.00, 10.10, '1', 0.40, 40.00, 40.40, 1, '采购部门', '财务部门', 137, '', 1, '2019-12-03 15:56:55', '采购部大哥', '2019-12-03 15:56:55', '采购部大哥', null, 1);
INSERT INTO `procurement_material` (id, name, order_id, material_id, procurement_unit, procurement_number, supplier, charge_unit, charge_number, delivery_date, unit_price, taxable_unit_price, tax_rate, tax_amount, total_price, tax_total_price, is_complimentary, demand_department, material_receiving_department, plan_material_id, status, sort, create_time, create_user, modify_time, modify_user, remark, is_enable) VALUES (3, '扳手', 4, 3, '个', 4, null, '', null, '2019-12-01', 10.00, 10.10, '1', 0.40, 40.00, 40.40, 1, '采购部门', '财务部门', 137, '', 1, '2019-12-03 15:56:55', '采购部大哥', '2019-12-03 15:56:55', '采购部大哥', null, 1);
INSERT INTO `procurement_material` (id, name, order_id, material_id, procurement_unit, procurement_number, supplier, charge_unit, charge_number, delivery_date, unit_price, taxable_unit_price, tax_rate, tax_amount, total_price, tax_total_price, is_complimentary, demand_department, material_receiving_department, plan_material_id, status, sort, create_time, create_user, modify_time, modify_user, remark, is_enable) VALUES (4, '扳手', 5, 3, '个', 4, null, '', null, '2019-12-01', 10.00, 10.10, '1', 0.40, 40.00, 40.40, 1, '采购部门', '财务部门', 137, '', 1, '2019-12-03 15:56:55', '采购部大哥', '2019-12-03 15:56:55', '采购部大哥', null, 1);
INSERT INTO `procurement_material` (id, name, order_id, material_id, procurement_unit, procurement_number, supplier, charge_unit, charge_number, delivery_date, unit_price, taxable_unit_price, tax_rate, tax_amount, total_price, tax_total_price, is_complimentary, demand_department, material_receiving_department, plan_material_id, status, sort, create_time, create_user, modify_time, modify_user, remark, is_enable) VALUES (5, '扳手', 6, 3, '个', 4, null, '', null, '2019-12-01', 10.00, 10.10, '1', 0.40, 40.00, 40.40, 1, '采购部门', '财务部门', 137, '', 1, '2019-12-03 15:56:55', '采购部大哥', '2019-12-03 15:56:55', '采购部大哥', null, 1);
INSERT INTO `procurement_material` (id, name, order_id, material_id, procurement_unit, procurement_number, supplier, charge_unit, charge_number, delivery_date, unit_price, taxable_unit_price, tax_rate, tax_amount, total_price, tax_total_price, is_complimentary, demand_department, material_receiving_department, plan_material_id, status, sort, create_time, create_user, modify_time, modify_user, remark, is_enable) VALUES (6, '扳手', 7, 3, '个', 4, null, '', null, '2019-12-01', 10.00, 10.10, '1', 0.40, 40.00, 40.40, 1, '采购部门', '财务部门', 1, '', 1, '2019-12-03 15:56:55', '采购部大哥', '2019-12-03 15:56:55', '采购部大哥', null, 1);

-- AuthServiceImplTest 测试数据
INSERT INTO `sys_user` (id, name, username, password, status, icon, email, phone, sort, create_time, create_user, modify_time, modify_user, remark, is_enable) VALUES (1, '管理员大哥', 'admin', '$2a$12$.4M8XvDph1phKGuVY1hDGe1ybHMD1RBLsOFx6vIXqhqm2eleHl7ny', 1, 'http://q0zlaui5t.bkt.clouddn.com/oneYard/avatar/74d964d9-dcb3-4f22-9625-a831479eeffc', '123456@163.com', '13712341555', 2, '2019-11-02 09:47:44', null, '2019-12-03 10:26:18', 'admin', null, 1);
INSERT INTO `sys_role` (id, name, department_id, description, sort, create_time, create_user, modify_time, modify_user, remark, is_enable) VALUES (1, 'ROLE_ADMIN', 1, '管理员', 1, '2019-11-02 09:47:41', null, '2019-11-02 09:47:41', null, null, 1);
INSERT INTO `sys_role` (id, name, department_id, description, sort, create_time, create_user, modify_time, modify_user, remark, is_enable) VALUES (2, 'ROLE_ACCESS', 1, '游客', 1, '2019-11-06 15:19:11', null, '2019-11-06 15:19:11', null, null, 1);
INSERT INTO `sys_user_role` (id, name, user_id, role_id, sort, create_time, create_user, modify_time, modify_user, remark, is_enable) VALUES (1, null, 1, 1, 1, '2019-11-19 02:34:09', null, '2019-11-19 02:34:09', null, null, 1);

-- SysUserServiceTest 测试数据
INSERT INTO `sys_department` (id, name, description, code, sort, create_time, create_user, modify_time, modify_user, remark, is_enable) VALUES (1, '系统', null, '01', 1, '2019-11-02 17:11:40', null, '2019-11-02 17:11:40', null, null, 1);

-- MaterialPlanSummaryServiceTest 测试数据
INSERT INTO `material_plan_summary` (id, name, sort, create_time, create_user, modify_time, modify_user, remark, is_enable) VALUES (1, '2019年05月份计划汇总', 201905, '2019-11-18 17:07:23', 'fgr@qq.com', '2019-11-18 17:07:23', 'fgr@qq.com', null, 1);
INSERT INTO `material_demand_plan` (id, name, plan_type, department_id, need_people, month, plan_status, approval_status, is_budget_plan, sort, create_time, create_user, modify_idea, modify_time, modify_user, remark, is_enable) VALUES (1, '20191119提报扳手等订单型需求计划', '紧急计划', 3, '刘传锦', '201905', 2, 2, 1, 1, '2019-11-18 16:58:17', 'japoul@163.com', null, '2019-11-18 18:28:18', 'fgr@qq.com', null, 1);
INSERT INTO `material_demand_plan` (id, name, plan_type, department_id, need_people, month, plan_status, approval_status, is_budget_plan, sort, create_time, create_user, modify_idea, modify_time, modify_user, remark, is_enable) VALUES (2, '20191119提报扳手等订单型需求计划', '年度计划', 3, '刘传锦', '201905', 2, 2, 1, 1, '2019-11-18 16:58:17', 'japoul@163.com', null, '2019-11-18 18:28:18', 'fgr@qq.com', null, 1);
INSERT INTO `material_demand_plan` (id, name, plan_type, department_id, need_people, month, plan_status, approval_status, is_budget_plan, sort, create_time, create_user, modify_idea, modify_time, modify_user, remark, is_enable) VALUES (3, '20191119提报扳手等订单型需求计划', '月度计划', 3, '刘传锦', '201905', 2, 2, 1, 1, '2019-11-18 16:58:17', 'japoul@163.com', null, '2019-11-18 18:28:18', 'fgr@qq.com', null, 1);
