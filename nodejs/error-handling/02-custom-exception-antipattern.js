class InvalidFormatError extends Error {
  constructor(message) {
    super(message);
    this.name = 'InvalidFormatError';
  }
}

class InvalidLengthError extends Error {
  constructor(message) {
    super(message);
    this.name = 'InvalidLengthError';
  }
}

function validateCpf(cpf) {
  if (isNaN(cpf)) {
    throw new InvalidFormatError(`CPF '${cpf}' must have only digits`);
  }
  if (cpf.length != 11) {
    throw new InvalidLengthError(`CPF '${cpf}' must have 11 digits`);
  }
}

for (const cpf of ['abc', '1234', '12345678901']) {
  try {
    validateCpf(cpf);
    console.log(`CPF '${cpf}' is valid`);
  } catch (error) {
    if (error instanceof InvalidFormatError || error instanceof InvalidLengthError) {
      console.log(`[${error.name}] - ${error.message}`);
      continue;
    }
    console.log('Unkown error');
  }
}
