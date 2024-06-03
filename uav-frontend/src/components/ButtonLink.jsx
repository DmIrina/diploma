import { Link } from "react-router-dom";

const ButtonLink = ({ text, toAction }) => {
  return (
      <Link className="btn btn-outline-primary m-2" to={toAction}>
        {text}
      </Link>
  );
};

export default ButtonLink;
