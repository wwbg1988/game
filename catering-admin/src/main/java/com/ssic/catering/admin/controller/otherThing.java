package com.ssic.catering.admin.controller;

public class otherThing {

	String hql = "select e.* from employees e,employees m where e.managerId=m.id and e.salary>m.salary ";
	private static final char[] data = new char[] { '零', '壹', '贰', '叁', '肆',
			'伍', '陆', '柒', '捌', '玖' };
	private static final char[] units = new char[] { '元', '拾', '佰', '仟', '万',
			'拾', '佰', '仟', '亿' };

	public static void main(String[] args) {
		int[] array = { 22, 54, 55, 78, 43, 98, 11, 56 };
		int temps = 0;
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array.length - 1; j++) {
				if (array[i] > array[j]) {

				} else {
					temps = array[i];
					array[i] = array[j];
					array[j] = temps;

				}
			}
		}
		for (int i = 0; i < array.length; i++) {
			System.out.println(array[i]);
		}
		// System.out.println(convert(135689123));
	}

	public static String convert(int money) {
		StringBuffer sbf = new StringBuffer();
		int unit = 0;
		while (money != 0) {
			sbf.insert(0, units[unit++]);
			int number = money % 10;
			sbf.insert(0, data[number]);
			System.out.println(sbf);
			money /= 10;
		}
		return sbf.toString();

	}
}