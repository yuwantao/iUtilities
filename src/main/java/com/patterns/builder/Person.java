package com.patterns.builder;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Created by yuwt on 2016/12/13.
 */
public class Person {
	private String name;
	private String gender;
	private float height;
	private float weight;

	private Person() {
	}

//	@Contract("null, _ -> fail; !null, null -> fail; !null, !null -> !null")
	@Contract("_, _ -> !null")
	public static Builder custom(@NotNull String name, @NotNull String gender) {
		return new Builder(name, gender);
	}

	public static class Builder {
		private String name;
		private String gender;
		private float height;
		private float weight;

		private Builder(String name, String gender) {
			this.name = name;
			this.gender = gender;
		}

		public Builder height(float height) {
			this.height = height;
			return this;
		}

		public Builder weight(float weight) {
			this.weight = weight;
			return this;
		}

		public Person build() {
			Person person = new Person();
			person.name = this.name;
			person.gender = this.gender;
			person.height = this.height;
			person.weight = this.weight;
			return person;
		}
	}

	public String getName() {
		return name;
	}

	public String getGender() {
		return gender;
	}

	public float getHeight() {
		return height;
	}

	public float getWeight() {
		return weight;
	}
}
