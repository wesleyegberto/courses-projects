function validateCpf(cpf) {
  const errors = [];

  if (isNaN(cpf)) {
    errors.push({
      name: 'InvalidFormatError',
      message: `CPF '${cpf}' must have only digits`
    });
  } else if (cpf.length != 11) {
    errors.push({
      name: 'InvalidLengthError',
      message: `CPF '${cpf}' must have 11 digits`
    });
  }

  return {
    valid: !errors.length,
    errors
  };
}

for (const cpf of ['abc', '1234', '12345678901']) {
  const { valid, errors } = validateCpf(cpf);

  if (valid) {
    console.log(`CPF '${cpf}' is valid`);
    continue;
  }

  errors.forEach(error => {
    console.log(`[${error.name}] - ${error.message}`);
  });
}
