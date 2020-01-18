import React, { useState, useEffect } from 'react';

import './style.css';

function DevForm({ onSubmit }) {
  const [githubUsername, setGithubUsername] = useState('');
  const [techs, setTechs] = useState('');
  const [latitude, setLatitude] = useState('');
  const [longitude, setLongitude] = useState('');

  // function to execute and when (array)
  useEffect(() => {
    navigator.geolocation.getCurrentPosition(
      position => {
        console.log(position);
        const { latitude, longitude } = position.coords;

        setLatitude(latitude);
        setLongitude(longitude);
      },
      err => {
        console.log(err);
      },
      {
        timeout: 30000
      }
    );
  }, []);

  async function handleSubmit(event) {
    event.preventDefault();

    await onSubmit({
      techs,
      latitude,
      longitude,
      github_username: githubUsername
    });

    setGithubUsername('');
    setTechs('');
  }

  return (
    <form onSubmit={handleSubmit}>
      <div className="input-block">
        <label htmlFor="github_username">Github username</label>
        <input type="text" name="github_username" value={githubUsername} onChange={e => setGithubUsername(e.target.value)} required />
      </div>

      <div className="input-block">
        <label htmlFor="techs">Technologies</label>
        <input type="text" name="techs" value={techs} onChange={e => setTechs(e.target.value)} required />
      </div>

      <div className="input-group">
        <div className="input-block">
          <label htmlFor="latitude">Latitude</label>
          <input type="text" name="latitude" value={latitude} onChange={e => setLatitude(e.target.value)} required />
        </div>

        <div className="input-block">
          <label htmlFor="longitude">Longitude</label>
          <input type="text" name="longitude" value={longitude} onChange={e => setLongitude(e.target.value)} required />
        </div>
      </div>

      <button type="submit">Save</button>
    </form>
  );
}

export default DevForm;
