module.exports = (techs) => {
  if (!techs || !techs.length) return [];
  return techs.split(',').map(tech => tech.trim().toLowerCase());
};
